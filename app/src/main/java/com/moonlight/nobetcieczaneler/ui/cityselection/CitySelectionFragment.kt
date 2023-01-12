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
import android.provider.Settings
import android.util.Log
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
import com.moonlight.nobetcieczaneler.R
import com.moonlight.nobetcieczaneler.databinding.FragmentCitySelectionBinding
import com.moonlight.nobetcieczaneler.ui.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CitySelectionFragment : Fragment(){

    private var _binding: FragmentCitySelectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CitySelectionViewModel by viewModels()
    private val sharedViewModel: MainViewModel by activityViewModels()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitySelectionBinding.inflate(inflater, container, false)

        getCities()

        binding.btnSubmit.setOnClickListener {
            submitButton()
        }
        binding.btnNearby.setOnClickListener {
            getNearbyList()
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        return binding.root
    }



    private fun getCities(){

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.getCities()

            binding.spinnerCity.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, viewModel.citiesNameList)

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
                viewModel.submitButton(viewModel.selectedCity, viewModel.selectedCounty!!)

                val nav = CitySelectionFragmentDirections.actionGoToPharmacyList(viewModel.pharmacyResponse, false)
                Navigation.findNavController(requireView()).navigate(nav)
            }
            else {
                Toast.makeText(requireContext(), getString(R.string.warning_county_not_selected), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getNearbyList(){
            getLocation()

            sharedViewModel.userLocation.observe(viewLifecycleOwner) {
                viewLifecycleOwner.lifecycleScope.launch {

                    if (it != null) {
                        viewModel.getNearbyList(sharedViewModel.userLocation.value!!.latitude, sharedViewModel.userLocation.value!!.longitude)

                        val nav = CitySelectionFragmentDirections.actionGoToPharmacyList(viewModel.pharmacyResponse, true)
                        Navigation.findNavController(requireView()).navigate(nav)
                    }
                    else {
                        Toast.makeText(requireContext(), getString(R.string.warning_location_error), Toast.LENGTH_SHORT).show()
                    }
                }
            }

    }

    private fun getLocation() {
        if(checkLocationPermission()){
            if(checkLocationEnabled()){
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                    val locationResult: Location?=it.result
                    if(locationResult == null){
                        Toast.makeText(requireContext(), getString(R.string.warning_location_not_found), Toast.LENGTH_SHORT).show()
                    }else {
                        sharedViewModel.saveLocation(LatLng(locationResult.latitude, locationResult.longitude))
                    }
                }
            }
            else
            {
                Snackbar.make(requireView(), getString(R.string.warning_location_disabled), Snackbar.LENGTH_SHORT).setAction(R.string.allow) {
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

}