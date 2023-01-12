package com.moonlight.nobetcieczaneler.data.model

import java.io.Serializable

data class Pharmacy(
    val Adresi: String?,
    val EczaneAdi: String?,
    val Sehir: String?,
    val Semt: String?,
    val Telefon: String?,
    val Telefon2: String?,
    val YolTarifi: String?,
    val ilce: String?,
    val latitude: Double,
    val longitude: Double,
    val distanceMt: Double,
    val distanceKm: Double,
    val distanceMil: Double
) : Serializable