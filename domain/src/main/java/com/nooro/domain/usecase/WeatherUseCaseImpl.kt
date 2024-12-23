package com.nooro.domain.usecase

import com.nooro.data.remote.model.WeatherResponseDTO
import com.nooro.data.remote.repository.WeatherRepository
import com.nooro.data.remote.result.NetworkResult
import com.nooro.domain.mapper.WeatherMapper
import com.nooro.domain.model.Weather

class WeatherUseCaseImpl(
    private val weatherRepository: WeatherRepository,
    private val weatherMapper: WeatherMapper,
) : WeatherUseCase {

    override suspend fun getCurrentCityInfo(
        query: String,
        aqi: Boolean,
        apiKey: String,
    ): NetworkResult<Weather> {
        return weatherRepository.getCurrentCityInfo(
            query = query,
            aqi = aqi,
            apiKey = apiKey
        ).handleNetWorkResult()
    }

    private fun NetworkResult<WeatherResponseDTO>.handleNetWorkResult(): NetworkResult<Weather> {
        return when (this) {
            is NetworkResult.Success -> {
                NetworkResult.Success(weatherMapper.map(data))
            }

            is NetworkResult.Error -> {
                NetworkResult.Error(message, cause, errorResponse)
            }
        }
    }
}