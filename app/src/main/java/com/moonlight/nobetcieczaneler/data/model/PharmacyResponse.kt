package com.moonlight.nobetcieczaneler.data.model

import java.io.Serializable

data class PharmacyResponse(
    val `data`: List<Pharmacy>,
    val message: String,
    val rowCount: Int,
    val status: String,
    val systemTime: Int
) : Serializable