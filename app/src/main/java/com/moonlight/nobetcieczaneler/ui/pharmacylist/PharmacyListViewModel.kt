package com.moonlight.nobetcieczaneler.ui.pharmacylist

import androidx.lifecycle.ViewModel
import com.moonlight.nobetcieczaneler.data.repo.PharmacyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PharmacyListViewModel @Inject constructor(var prepo: PharmacyRepository) : ViewModel() {

}