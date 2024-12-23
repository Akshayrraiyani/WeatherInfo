package com.nooro.data.remote.repository

import com.nooro.data.remote.api.WeatherService
import com.nooro.data.remote.model.WeatherResponseDTO
import com.nooro.data.remote.result.NetworkResult
import com.nooro.data.remote.result.handleException
import com.nooro.data.remote.util.handleBaseResponse

class WeatherRepository(private val weatherService: WeatherService) {

    suspend fun getCurrentCityInfo(
        query: String,
        aqi: Boolean,
        apiKey: String,
    ): NetworkResult<WeatherResponseDTO> {
        return try {
            weatherService.getCurrentCityInfo(
                query = query,
                aqi = aqi,
                apiKey = apiKey
            ).handleBaseResponse()
        } catch (e: Exception) {
            handleException(e)
        }
    }
}