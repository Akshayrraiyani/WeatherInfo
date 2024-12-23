package com.nooro.weather

import android.app.Application
import com.nooro.data.di.retrofitModule
import com.nooro.domain.di.useCaseModule
import com.nooro.weather.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApplication)
            modules(listOf(viewModelModule, useCaseModule, retrofitModule))
        }
    }
}
