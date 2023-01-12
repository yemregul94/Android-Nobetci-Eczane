package com.moonlight.nobetcieczaneler.ui.pharmacymap

import androidx.lifecycle.ViewModel
import com.moonlight.nobetcieczaneler.data.repo.PharmacyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PharmacyMapViewModel @Inject constructor(var prepo: PharmacyRepository) : ViewModel() {

}