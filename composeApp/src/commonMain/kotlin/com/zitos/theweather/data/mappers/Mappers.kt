package com.zitos.theweather.data.mappers

import com.zitos.theweather.data.model.WeatherResponse
import com.zitos.theweather.data.model.details.WeatherDetailsDTO
import com.zitos.theweather.domain.model.Weather
import com.zitos.theweather.domain.model.WeatherItem

fun WeatherResponse.toDomain(): Weather {
    return Weather(
        temperature = main.temp.toFloat().minus(273f),
        icon = weather.first().icon,
        country = name
    )
}

fun List<WeatherDetailsDTO>.toDomain(): List<WeatherItem> {
    return map {
        WeatherItem(
            feelsLike = it.main.temp.toFloat().minus(273f),
            time = it.dt.toString(),
            icon = it.weather.first().icon
        )
    }
}

expect fun formatFloatUpToTwoDecimalPlaces(float: Float): String