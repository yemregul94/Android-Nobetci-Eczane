package com.moonlight.nobetcieczaneler.ui.cityselection

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.material.snackbar.Snackbar
import com.moonlight.nobetcieczaneler.R
import com.moonlight.nobetcieczaneler.databinding.FragmentCitySelectionBinding
import com.moonlight.nobetcieczaneler.ui.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CitySelectionFragment : Fragment(){

    private var _binding: FragmentCitySelectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CitySelectionViewModel by viewModels()
    private val sharedViewModel: MainViewModel by activityViewModels()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var lastLocation = LatLng(0.0, 0.0)
    private var lastCityPos = 0
    private var lastCity = ""
    private var lastCounty = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitySelectionBinding.inflate(inflater, container, false)


        sharedViewModel.isConnected.observe(viewLifecycleOwner) {
            if (it) {
                getCities()
            }
        }

        binding.btnSubmit.setOnClickListener {
            if(sharedViewModel.checkConnection()){
                submitButton()
            }
            else {
                Toast.makeText(requireContext(), getString(R.string.warning_connection_not_found), Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnNearby.setOnClickListener {
            disableButton(it)

            if(sharedViewModel.checkConnection()){
                getNearbyList()
            }
            else {
                Toast.makeText(requireContext(), getString(R.string.warning_connection_not_found), Toast.LENGTH_SHORT).show()
            }
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        return binding.root
    }

    private fun disableButton(view: View) {
        view.setEnabled(false)
            Handler().postDelayed({ view.setEnabled(true) }, 5000)
    }


    private fun getCities(){

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.getCities()

            binding.spinnerCity.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, viewModel.citiesNameList)
            binding.spinnerCity.setSelection(lastCityPos)

            binding.spinnerCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (view != null){

                        (parent?.getChildAt(0) as? TextView)?.apply {
                            setTextColor(Color.WHITE)
                            textSize = 20f
                        }

                        val city = viewModel.citiesMap[parent?.getItemAtPosition(position)].toString()
                        lastCityPos = position
                        viewModel.selectedCity = city
                        getCounties(city)
                    }
                    else if(parent?.selectedItemPosition == 0){
                        binding.spinnerCity.setSelection(0, false)
                    }
                }
            }
        }

    }

    private fun getCounties(city: String){

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.getCounties(city)

            binding.spinnerCounty.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, viewModel.countiesNameList)

            binding.spinnerCounty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    (parent?.getChildAt(0) as? TextView)?.apply {
                        setTextColor(Color.WHITE)
                        textSize = 20f
                    }

                    val county = viewModel.countiesMap[parent?.getItemAtPosition(position)].toString()
                    viewModel.selectedCounty = county
                }
            }
        }

    }

    private fun submitButton(){
        viewLifecycleOwner.lifecycleScope.launch {
            if(viewModel.selectedCounty != null){
                if(viewModel.selectedCity != lastCity || viewModel.selectedCounty != lastCounty){
                    viewModel.submitButton(viewModel.selectedCity, viewModel.selectedCounty!!)
                    lastCity = viewModel.selectedCity
                    lastCounty = viewModel.selectedCounty!!
                }

                val nav = viewModel.pharmacyResponse?.let { CitySelectionFragmentDirections.actionGoToPharmacyList(it, false)
                }
                if (nav != null) {
                    Navigation.findNavController(requireView()).navigate(nav)
                }
            }
            else {
                Toast.makeText(requireContext(), getString(R.string.warning_county_not_selected), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getNearbyList(){
            getLocation()

            sharedViewModel.userLocation.observe(viewLifecycleOwner) {
                if(lastLocation != it){
                    viewLifecycleOwner.lifecycleScope.launch {

                        if (it != null) {
                            viewModel.getNearbyList(sharedViewModel.userLocation.value!!.latitude, sharedViewModel.userLocation.value!!.longitude)

                            try {
                                val nav = viewModel.nearbyPharmacyResponse?.let { it1 -> CitySelectionFragmentDirections.actionGoToPharmacyList(it1, true)
                                }
                                if (nav != null) {
                                    Navigation.findNavController(requireView()).navigate(nav)
                                }
                            }
                            catch (_: Exception){

                            }
                        }
                        else {
                            Toast.makeText(requireContext(), getString(R.string.warning_location_error), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else {
                    try {
                        val nav = viewModel.nearbyPharmacyResponse?.let { it1 -> CitySelectionFragmentDirections.actionGoToPharmacyList(it1, true)
                        }
                        if (nav != null) {
                            Navigation.findNavController(requireView()).navigate(nav)
                        }
                    }
                    catch (_: Exception){

                    }
                }
            }
    }

    private fun getLocation() {
        if(checkLocationPermission()){
            if(checkLocationEnabled()){
                fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, object : CancellationToken(){
                    override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken {
                        return CancellationTokenSource().token
                    }
                    override fun isCancellationRequested(): Boolean {
                        return false
                    }
                }).addOnCompleteListener {
                    val locationResult: Location?=it.result

                    if(locationResult == null){
                        Toast.makeText(requireContext(), getString(R.string.warning_location_not_found), Toast.LENGTH_SHORT).show()
                    }else {
                        sharedViewModel.saveLocation(LatLng(locationResult.latitude, locationResult.longitude))
                        lastLocation = LatLng(locationResult.latitude, locationResult.longitude)
                    }
                }

            }
            else
            {
                Snackbar.make(requireView(), getString(R.string.warning_location_disabled), Snackbar.LENGTH_SHORT).setAction(R.string.enable) {
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    requireActivity().startActivity(intent)
                }.show()
            }
        }
        else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), 100)
        }
    }


    private fun checkLocationPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == 100){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLocation()
            }
            else{
                openAppSettings()
            }
        }

    }

    private fun openAppSettings(){
        Toast.makeText(requireContext(), getString(R.string.warning_location_permission), Toast.LENGTH_SHORT).show()
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + requireContext().packageName)
        requireActivity().startActivity(intent)
    }

    private fun checkLocationEnabled(): Boolean {
        val locationManager: LocationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}