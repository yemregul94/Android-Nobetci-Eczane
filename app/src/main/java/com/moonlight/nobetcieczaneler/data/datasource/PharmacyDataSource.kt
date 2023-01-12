package com.moonlight.nobetcieczaneler.data.datasource

import com.moonlight.nobetcieczaneler.BuildConfig
import com.moonlight.nobetcieczaneler.data.model.CitiesReponse
import com.moonlight.nobetcieczaneler.data.model.CountiesResponse
import com.moonlight.nobetcieczaneler.data.model.PharmacyResponse
import com.moonlight.nobetcieczaneler.data.retrofit.PharmacyDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PharmacyDataSource(var pdao: PharmacyDao) {

    val APIKEY = BuildConfig.API_KEY

    suspend fun getCities() : CitiesReponse =
        withContext(Dispatchers.IO){
            pdao.getCities(APIKEY)
        }

    suspend fun getCounties(city: String) : CountiesResponse =
        withContext(Dispatchers.IO){
            pdao.getCounties(city, APIKEY)
        }

    suspend fun getPharmacyList(city: String, county: String) : PharmacyResponse =
        withContext(Dispatchers.IO){
            pdao.getPharmacyList(city, county, APIKEY)
        }

    suspend fun getNearbyList(latitude: Double, longitude: Double) : PharmacyResponse =
        withContext(Dispatchers.IO){
            pdao.getNearbyList(latitude, longitude, APIKEY)
        }
}