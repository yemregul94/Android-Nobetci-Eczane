package com.moonlight.nobetcieczaneler.ui.pharmacylist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.moonlight.nobetcieczaneler.R
import com.moonlight.nobetcieczaneler.data.model.Pharmacy
import com.moonlight.nobetcieczaneler.databinding.ItemPharmacyListBinding

class PharmacyListAdapter(var pharmacyList: List<Pharmacy>, var isNearby: Boolean) : RecyclerView.Adapter<PharmacyListAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemPharmacyListBinding) : RecyclerView.ViewHolder(binding.root){
        var binding: ItemPharmacyListBinding
        init {
            this.binding = binding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemPharmacyListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_pharmacy_list, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pharmacy = pharmacyList.get(position)
        val bind = holder.binding
        bind.pharmacyObject = pharmacy
        bind.isNearby = isNearby

        bind.cardItem.setOnClickListener {
            val nav = PharmacyListFragmentDirections.actionGoToPharmacyDetails(pharmacy)
            Navigation.findNavController(it).navigate(nav)
        }
    }

    override fun getItemCount(): Int {
        return pharmacyList.size
    }
}