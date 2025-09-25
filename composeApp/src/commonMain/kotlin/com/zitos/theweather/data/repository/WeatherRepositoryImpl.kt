package com.zitos.theweather.data.repository

import com.zitos.theweather.data.mappers.toDomain
import com.zitos.theweather.data.remote.ApiService
import com.zitos.theweather.domain.model.Weather
import com.zitos.theweather.domain.model.WeatherItem
import com.zitos.theweather.domain.repository.WeatherRepository

class WeatherRepositoryImpl(private val apiService: ApiService) : WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double): Weather {
        return apiService.getWeather(lat,lon).toDomain()
    }

    override suspend fun getWeatherDetails(lat: Double, lon: Double): List<WeatherItem> {
        return apiService.getWeatherDetails(lat,lon).list.toDomain()
    }
}