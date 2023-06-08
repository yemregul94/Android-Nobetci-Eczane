package com.moonlight.nobetcieczaneler.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng


class MainViewModel : ViewModel() {

    private var _userLocation = MutableLiveData<LatLng>()
    val userLocation : LiveData<LatLng> = _userLocation

    private var _isConnected = MutableLiveData<Boolean>()
    val isConnected : LiveData<Boolean> = _isConnected

    init {
        _isConnected.value = false
    }

    fun saveLocation(location: LatLng) {
        _userLocation.value = location
    }

    fun checkConnection() : Boolean{
        return isConnected.value!!
    }

    fun changeConnection(connectionStatus: Boolean){
        _isConnected.value = connectionStatus
    }
}