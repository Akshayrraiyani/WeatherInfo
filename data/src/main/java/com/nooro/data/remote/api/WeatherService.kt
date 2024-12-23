package com.nooro.data.remote.api

import com.nooro.data.remote.model.WeatherResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET(ApiEndpoints.CURRENT_CITY_DATA)
    suspend fun getCurrentCityInfo(
        @Query(ApiParameters.Q) query: String,
        @Query(ApiParameters.AQI) aqi: Boolean,
        @Query(ApiParameters.KEY) apiKey: String,
    ): Response<WeatherResponseDTO>
}