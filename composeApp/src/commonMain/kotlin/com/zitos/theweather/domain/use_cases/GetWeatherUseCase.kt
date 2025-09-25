package com.zitos.theweather.domain.use_cases

import com.zitos.theweather.domain.model.Weather
import com.zitos.theweather.domain.repository.WeatherRepository

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(lat: Double, lon: Double): Result<Weather> {
        return try {
            val response = weatherRepository.getWeather(lat, lon)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}