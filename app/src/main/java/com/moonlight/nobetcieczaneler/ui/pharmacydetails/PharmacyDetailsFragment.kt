package com.moonlight.nobetcieczaneler.ui.pharmacydetails


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.moonlight.nobetcieczaneler.R
import com.moonlight.nobetcieczaneler.databinding.FragmentPharmacyDetailsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PharmacyDetailsFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentPharmacyDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PharmacyDetailsViewModel by viewModels()
    private lateinit var mMap: GoogleMap
    private lateinit var location: LatLng
    private lateinit var title: String
    private lateinit var phone: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPharmacyDetailsBinding.inflate(inflater, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        loadDetails()

        binding.tvPhone.setOnClickListener {
            callPharmacy()
        }
        binding.layoutMap.setOnClickListener {
            openGoogleMaps()
        }

        return binding.root
    }

    private fun loadDetails(){
        val bundle: PharmacyDetailsFragmentArgs by navArgs()
        val pharmacy = bundle.pharmacy

        binding.pharmacyObject = pharmacy

        location = LatLng(pharmacy.latitude, pharmacy.longitude)
        title = pharmacy.EczaneAdi!!
        phone = pharmacy.Telefon!!
    }

    private fun checkCallPermission(){
        if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), 101)
        }
    }

    private fun callPharmacy(){
        checkCallPermission()
        if(ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            Snackbar.make(requireView(), getString(R.string.warning_call_permission), Snackbar.LENGTH_SHORT).setAction(
                getString(R.string.allow)) { openSettings() }.show()
        }else{
            val phoneIntent = Intent(Intent.ACTION_CALL)
            phoneIntent.data = Uri.parse("tel:$phone")
            requireActivity().startActivity(phoneIntent)
        }
    }

    private fun openSettings(){
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + requireContext().packageName)
        requireActivity().startActivity(intent)
    }

    private fun openGoogleMaps(){
        val gmmIntentUri  = Uri.parse("geo:0,0?q=${location.latitude},${location.longitude}($title)")
        val intent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")
        requireActivity().startActivity(intent)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.addMarker(MarkerOptions()
            .position(location)
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacy_pin_64))
            .title(title))?.showInfoWindow()
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))

    }
}