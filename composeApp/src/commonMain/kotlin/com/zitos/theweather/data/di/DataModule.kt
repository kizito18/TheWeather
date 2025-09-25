package com.zitos.theweather.data.di

import com.zitos.theweather.data.remote.ApiService
import com.zitos.theweather.data.remote.KtorClient
import com.zitos.theweather.data.repository.WeatherRepositoryImpl
import com.zitos.theweather.domain.repository.WeatherRepository
import org.koin.dsl.module


val dataModule =  module{
    factory { ApiService(KtorClient.client) }
    factory<WeatherRepository> { WeatherRepositoryImpl(get()) }
}