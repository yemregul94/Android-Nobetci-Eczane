package com.moonlight.nobetcieczaneler.ui.pharmacylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.moonlight.nobetcieczaneler.R
import com.moonlight.nobetcieczaneler.data.model.Pharmacy
import com.moonlight.nobetcieczaneler.data.model.PharmacyResponse
import com.moonlight.nobetcieczaneler.databinding.FragmentPharmacyListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PharmacyListFragment : Fragment() {

    private var _binding: FragmentPharmacyListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PharmacyListViewModel by viewModels()

    private lateinit var pharmacyResponse: PharmacyResponse
    private lateinit var pharmacyList: List<Pharmacy>

    private var isNearby = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPharmacyListBinding.inflate(inflater, container, false)

        checkListType()

        binding.layoutMap.setOnClickListener {
            loadMap()
        }

        loadList()

        return binding.root
    }

    private fun loadList() {
        val bundle: PharmacyListFragmentArgs by navArgs()
        isNearby = bundle.isNearby

        pharmacyResponse = bundle.pharmacyResponse
        pharmacyList = pharmacyResponse.data

        if(pharmacyList.isEmpty()){
            binding.tvWarning.isVisible = true
            if(isNearby){
                binding.tvWarning.text = getString(R.string.no_pharmacy_nearby)
                binding.layoutMap.isVisible = false
            }else{
                binding.tvWarning.text = getString(R.string.no_pharmacy_in_selected_area)
            }
        }

        val adapter = if(isNearby){
            PharmacyListAdapter(pharmacyList.sortedBy { it.distanceMt }, isNearby)

        }else{
            PharmacyListAdapter(pharmacyList.sortedBy { it.ilce }, isNearby)
        }

        binding.rvPharmacyList.apply {
            this.layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }

    }

    private fun checkListType(){
        binding.isNearby = isNearby
    }

    private fun loadMap(){
        val nav = PharmacyListFragmentDirections.actionGoToPharmacyMap(pharmacyResponse)
        Navigation.findNavController(requireView()).navigate(nav)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
