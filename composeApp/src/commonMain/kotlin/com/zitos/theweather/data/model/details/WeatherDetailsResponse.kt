package com.zitos.theweather.data.model.details

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDetailsResponse(
    val list: List<WeatherDetailsDTO>,
)