package com.zitos.theweather.ui.di

import com.zitos.theweather.ui.view_models.WeatherViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.module.Module
import org.koin.dsl.module

private val viewModelModule = module {
    single { WeatherViewModel(get(), get()) }
}

actual fun sharedViewModelModules(): Module = viewModelModule

object ProvideViewModel : KoinComponent {

    fun getWeatherViewModel() = get<WeatherViewModel>()

}
