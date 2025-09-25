package com.zitos.theweather.di

import com.zitos.theweather.data.di.dataModule
import com.zitos.theweather.domain.di.domainModule
import com.zitos.theweather.ui.di.sharedViewModelModules
import org.koin.core.context.startKoin

fun initKoin(){
    startKoin {
        modules(dataModule + domainModule + sharedViewModelModules())
    }
}