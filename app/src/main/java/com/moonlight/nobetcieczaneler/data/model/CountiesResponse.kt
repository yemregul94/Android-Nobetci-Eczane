package com.moonlight.nobetcieczaneler.data.model

data class CountiesResponse(
    val `data`: List<Counties>,
    val message: String,
    val rowCount: Int,
    val status: String,
    val systemTime: Int
)