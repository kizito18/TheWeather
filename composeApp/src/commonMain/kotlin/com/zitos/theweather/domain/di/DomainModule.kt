package com.zitos.theweather.domain.di

import com.zitos.theweather.domain.use_cases.GetWeatherDetailsUseCase
import com.zitos.theweather.domain.use_cases.GetWeatherUseCase
import org.koin.dsl.module

val domainModule = module{
    factory { GetWeatherUseCase(get()) }
    factory { GetWeatherDetailsUseCase(get()) }
}