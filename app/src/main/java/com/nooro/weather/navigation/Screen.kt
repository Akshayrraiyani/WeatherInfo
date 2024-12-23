package com.nooro.weather.navigation

sealed class Screen(
    val route: String,
) {
    data object Splash : Screen("splash")

    data object WeatherInfoScreen : Screen("weatherInfo")
}
