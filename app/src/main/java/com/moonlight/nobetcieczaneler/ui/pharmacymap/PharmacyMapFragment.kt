package com.moonlight.nobetcieczaneler.ui.pharmacymap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.moonlight.nobetcieczaneler.R
import com.moonlight.nobetcieczaneler.data.model.Pharmacy
import com.moonlight.nobetcieczaneler.databinding.FragmentPharmacyMapBinding
import com.moonlight.nobetcieczaneler.ui.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PharmacyMapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentPharmacyMapBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PharmacyMapViewModel by viewModels()
    private val sharedViewModel: MainViewModel by activityViewModels()

    private lateinit var mMap: GoogleMap
    private lateinit var pharmacyList: List<Pharmacy>

    private lateinit var userLocation: LatLng

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPharmacyMapBinding.inflate(inflater, container, false)

        getLocation()
        loadMapData()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        return binding.root
    }

    private fun getLocation(){
        sharedViewModel.userLocation.observe(viewLifecycleOwner) { location ->
            userLocation = location
        }
    }

    private fun loadMapData(){
        val bundle: PharmacyMapFragmentArgs by navArgs()
        pharmacyList = bundle.pharmacyResponse.data
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        pharmacyList.forEach {
            val title = it.EczaneAdi
            val location = LatLng(it.latitude, it.longitude)

            mMap.addMarker(
                MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.pharmacy_pin_64))
                    .position(location)
                    .title(title))
        }

        mMap.addMarker(MarkerOptions()
            .position(userLocation)
            .icon(BitmapDescriptorFactory.fromBitmap(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_user_location)!!.toBitmap()))
            .title(getString(R.string.your_location)))?.showInfoWindow()
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 13f))
    }

}