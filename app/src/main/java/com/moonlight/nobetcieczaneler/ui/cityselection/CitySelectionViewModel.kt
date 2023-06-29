package com.moonlight.nobetcieczaneler.ui.cityselection

import androidx.lifecycle.ViewModel
import com.moonlight.nobetcieczaneler.data.model.PharmacyResponse
import com.moonlight.nobetcieczaneler.data.repo.PharmacyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CitySelectionViewModel @Inject constructor(var prepo: PharmacyRepository) : ViewModel() {

    var citiesMap = LinkedHashMap<String, String>()
    var citiesNameList = ArrayList<String>()
    lateinit var selectedCity: String

    var countiesMap = LinkedHashMap<String, String>()
    var countiesNameList = ArrayList<String>()
    var selectedCounty: String? = null

    var pharmacyResponse: PharmacyResponse? = null
    var nearbyPharmacyResponse: PharmacyResponse? = null


    suspend fun getCities(){

        if (citiesNameList.isEmpty()){
            val response = prepo.getCities()

            if(response.status == "success"){

                response.data.forEach {
                    citiesMap.put(it.SehirAd, it.SehirSlug)
                    citiesNameList.add(it.SehirAd)
                }
            }
            else {
                //Log.d("msg", response.message)
            }
        }
    }


    suspend fun getCounties(city: String){

        countiesNameList.clear()
        val response = prepo.getCounties(city)

        if(response.status == "success"){
            countiesNameList.add("Tüm İlçeler")
            countiesMap["Tüm İlçeler"] = ""

            response.data.forEach {
                countiesMap[it.ilceAd] = it.ilceSlug
                countiesNameList.add(it.ilceAd)
            }
        }
        else {
            //Log.d("msg", response.message)
        }
    }

    suspend fun submitButton(city: String, county: String){

        val response = prepo.getPharmacyList(city, county)
        pharmacyResponse = response
    }

    suspend fun getNearbyList(latitude: Double, longitude: Double){
        val response = prepo.getNearbyList(latitude, longitude)
        nearbyPharmacyResponse = response
    }
}