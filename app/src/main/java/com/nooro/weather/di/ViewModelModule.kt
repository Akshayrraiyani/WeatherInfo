package com.nooro.weather.di

import android.app.Application
import com.nooro.weather.presentation.viewmodel.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        WeatherViewModel(get(), get<Application>())
    }
}
