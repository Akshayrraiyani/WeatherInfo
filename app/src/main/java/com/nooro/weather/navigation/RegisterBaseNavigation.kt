package com.nooro.weather.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nooro.weather.presentation.view.weather.WeatherScreen
import com.nooro.weather.presentation.view.splash.SplashScreen
import com.nooro.weather.presentation.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavHostController.RegisterBaseNavigation() {
    val weatherVideModel: WeatherViewModel = koinViewModel()

    NavHost(this, startDestination = Screen.Splash.route) {

        composable(route = Screen.Splash.route) {
            SplashScreen(navController = this@RegisterBaseNavigation)
        }

        composable(route = Screen.WeatherInfoScreen.route) {
            WeatherScreen(weatherVideModel)
        }
    }
}
