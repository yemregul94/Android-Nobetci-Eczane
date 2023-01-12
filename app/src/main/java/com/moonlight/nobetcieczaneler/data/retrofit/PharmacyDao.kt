package com.moonlight.nobetcieczaneler.data.retrofit

import com.moonlight.nobetcieczaneler.data.model.CitiesReponse
import com.moonlight.nobetcieczaneler.data.model.CountiesResponse
import com.moonlight.nobetcieczaneler.data.model.PharmacyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PharmacyDao {

    @GET("pharmacy/city")
    suspend fun getCities(@Query("apikey") apikey: String) : CitiesReponse

    @GET("pharmacy/city")
    suspend fun getCounties(@Query("city") city: String, @Query("apikey") apikey: String) : CountiesResponse

    @GET("pharmacyLink")
    suspend fun getPharmacyList(@Query("city") city: String, @Query("county") county: String, @Query("apikey") apikey: String) : PharmacyResponse

    @GET("pharmacy/distance")
    suspend fun getNearbyList(@Query("latitude") latitute: Double, @Query("longitude") longitude: Double, @Query("apikey") apikey: String) : PharmacyResponse

}