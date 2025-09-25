package com.zitos.theweather.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CoordDTO(
    val lat: Double,
    val lon: Double
)