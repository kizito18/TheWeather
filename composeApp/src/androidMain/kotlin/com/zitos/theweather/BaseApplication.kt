package com.zitos.theweather

import android.app.Application
import com.zitos.theweather.data.di.dataModule
import com.zitos.theweather.domain.di.domainModule
import com.zitos.theweather.ui.di.sharedViewModelModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(dataModule + domainModule + sharedViewModelModules())
        }
    }
}