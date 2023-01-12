package com.moonlight.nobetcieczaneler.data.retrofit

import com.moonlight.nobetcieczaneler.util.BASE_URL


class ApiUtils {
    companion object{
        fun getPharmacyDao(): PharmacyDao{
            return RetrofitClient.getClient(BASE_URL).create(PharmacyDao::class.java)
        }
    }
}