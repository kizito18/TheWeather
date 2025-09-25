package com.zitos.theweather.domain.use_cases

import com.zitos.theweather.domain.model.WeatherItem
import com.zitos.theweather.domain.repository.WeatherRepository

class GetWeatherDetailsUseCase (private val weatherRepository: WeatherRepository) {

    suspend operator fun invoke(lat:Double, lon:Double) :Result<List<WeatherItem>> {
        return try {
            val response = weatherRepository.getWeatherDetails(lat,lon)
            Result.success(response)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

}