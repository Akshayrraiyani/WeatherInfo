package com.nooro.weather.presentation.viewmodel

import android.app.Application
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nooro.data.remote.result.NetworkResult
import com.nooro.domain.di.getSharedPrefs
import com.nooro.domain.model.Weather
import com.nooro.domain.usecase.WeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WeatherViewModel(
    private val weatherUseCase: WeatherUseCase,
    private val application: Application,
) : KoinComponent, ViewModel() {

    companion object {
        private const val CITY_NAME = "CITY_NAME"
    }

    private val sharedPreferences by inject<SharedPreferences>()
    private val sharedPreferencesEditor by inject<SharedPreferences.Editor>()

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState

    init {
        getSavedCityWeatherInfo()?.let {
            callWeatherAPI(it, false)
        }
    }

    fun callWeatherAPI(query: String, isFromSearchResult: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(loading = true) }
            val weatherNetworkResult = weatherUseCase.getCurrentCityInfo(
                query = query,
                apiKey = application.applicationContext.getString(com.nooro.data.R.string.apiKey)
            )
            when (weatherNetworkResult) {
                is NetworkResult.Success -> {
                    if (isFromSearchResult) {
                        _uiState.update {
                            it.copy(
                                error = null,
                                loading = false,
                                searchWeatherData = weatherNetworkResult.data,
                                mainWeatherData = null,
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                error = null,
                                loading = false,
                                mainWeatherData = weatherNetworkResult.data,
                                searchWeatherData = null,
                            )
                        }
                    }
                }

                is NetworkResult.Error -> {
                    if (isFromSearchResult) {
                        _uiState.update {
                            it.copy(
                                loading = false,
                                mainWeatherData = null,
                                error = weatherNetworkResult.errorResponse?.let { errorResponse ->
                                    errorResponse.error.message.toString()
                                } ?: run { weatherNetworkResult.message },
                                searchWeatherData = null,
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                loading = false,
                                mainWeatherData = null,
                                error = weatherNetworkResult.errorResponse?.let { errorResponse ->
                                    errorResponse.error.message.toString()
                                } ?: run { weatherNetworkResult.message },
                                searchWeatherData = null,
                            )
                        }
                    }
                }
            }
        }
    }

    fun saveCityWeatherInfo(weatherData: Weather) {
        sharedPreferencesEditor.putString(CITY_NAME, weatherData.cityName)
        sharedPreferencesEditor.apply()
        _uiState.update {
            it.copy(
                error = null,
                loading = false,
                searchWeatherData = null,
                mainWeatherData = weatherData,
            )
        }
    }

    fun getSavedCityWeatherInfo(): String? {
        return sharedPreferences.getString(CITY_NAME, null)
    }

    data class WeatherUiState(
        val loading: Boolean = false,
        val mainWeatherData: Weather? = null,
        val searchWeatherData: Weather? = null,
        val error: String? = null,
    )
}