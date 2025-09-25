package com.zitos.theweather.domain.repository

import com.zitos.theweather.domain.model.Weather
import com.zitos.theweather.domain.model.WeatherItem

interface WeatherRepository {

    suspend fun getWeather(lat: Double, lon: Double): Weather

    suspend fun getWeatherDetails(lat: Double, lon: Double): List<WeatherItem>

}