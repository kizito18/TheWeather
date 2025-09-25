package com.zitos.theweather.data.model.details

import com.zitos.theweather.data.model.MainDTO
import com.zitos.theweather.data.model.WeatherDTO
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDetailsDTO(
    val dt: Int,
    val main: MainDTO,
    val weather: List<WeatherDTO>,
)