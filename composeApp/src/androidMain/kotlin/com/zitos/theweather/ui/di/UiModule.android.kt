package com.zitos.theweather.ui.di

import com.zitos.theweather.ui.view_models.WeatherViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

private val viewModelModule = module{

    viewModel { WeatherViewModel(get(), get()) }
}

actual fun sharedViewModelModules(): Module = viewModelModule