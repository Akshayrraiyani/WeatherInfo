package com.nooro.domain.di

import android.content.Context
import android.content.SharedPreferences
import com.nooro.domain.mapper.WeatherMapper
import com.nooro.domain.usecase.WeatherUseCaseImpl
import com.nooro.domain.usecase.WeatherUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCaseModule = module {
    factoryOf(::WeatherUseCaseImpl) { bind<WeatherUseCase>() }
    single { WeatherMapper() }

    single{
        getSharedPrefs(androidContext())
    }

    single<SharedPreferences.Editor> {
        getSharedPrefs(androidContext()).edit()
    }

}

fun getSharedPrefs(androidApplication: Context): SharedPreferences{
    return androidApplication.getSharedPreferences("WEATHER_INFO",  Context.MODE_PRIVATE)
}
