package com.nooro.domain.model

data class Weather(
    var cityName: String = "",
    var feelsLikeC: Double = 0.0,
    var humidity: Int = 0,
    var uv: Double = 0.0,
    var icon: String = "",
    var tempC: Double = 0.0
)
