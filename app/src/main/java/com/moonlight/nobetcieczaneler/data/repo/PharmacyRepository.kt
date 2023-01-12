package com.moonlight.nobetcieczaneler.data.repo

import com.moonlight.nobetcieczaneler.data.datasource.PharmacyDataSource
import com.moonlight.nobetcieczaneler.data.model.CitiesReponse
import com.moonlight.nobetcieczaneler.data.model.CountiesResponse
import com.moonlight.nobetcieczaneler.data.model.PharmacyResponse

class PharmacyRepository(var pds: PharmacyDataSource) {
    suspend fun getCities(): CitiesReponse = pds.getCities()

    suspend fun getCounties(city: String): CountiesResponse = pds.getCounties(city)

    suspend fun getPharmacyList(city: String, county: String): PharmacyResponse = pds.getPharmacyList(city, county)

    suspend fun getNearbyList(latitude: Double, longitude: Double): PharmacyResponse = pds.getNearbyList(latitude, longitude)
}