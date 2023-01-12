package com.moonlight.nobetcieczaneler.data.model

data class CitiesReponse(
    val `data`: List<Cities>,
    val message: String,
    val rowCount: Int,
    val status: String,
    val systemTime: Int
)