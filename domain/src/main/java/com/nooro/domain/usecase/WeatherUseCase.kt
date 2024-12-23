package com.nooro.domain.usecase

import com.nooro.data.remote.result.NetworkResult
import com.nooro.domain.model.Weather

interface WeatherUseCase {
    suspend fun getCurrentCityInfo(
        query: String,
        aqi: Boolean = true,
        apiKey: String,
    ): NetworkResult<Weather>
}