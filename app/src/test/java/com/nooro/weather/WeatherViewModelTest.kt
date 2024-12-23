package com.nooro.weather

import android.app.Application
import android.content.SharedPreferences
import app.cash.turbine.test
import com.nooro.data.remote.result.NetworkResult
import com.nooro.domain.model.Weather
import com.nooro.domain.usecase.WeatherUseCase
import com.nooro.weather.presentation.viewmodel.WeatherViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    private lateinit var weatherViewModel: WeatherViewModel
    private val weatherUseCase = mockk<WeatherUseCase>(relaxed = true)
    private val sharedPreferences = mockk<SharedPreferences>(relaxed = true)
    private val sharedPreferencesEditor = mockk<SharedPreferences.Editor>(relaxed = true)
    private val application: Application = mockk(relaxed = true)

    private val testDispatcher = UnconfinedTestDispatcher()

    private val testModule = module {
        single { weatherUseCase }
        single { sharedPreferences }
        single { sharedPreferencesEditor }
        single { application }
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        startKoin {
            modules(testModule)
        }

        val cityName = "New York"
        every { sharedPreferences.getString("CITY_NAME", null) } returns cityName

        every { sharedPreferences.edit() } returns sharedPreferencesEditor
        every { sharedPreferencesEditor.putString(any(), any()) } returns sharedPreferencesEditor
        justRun { sharedPreferencesEditor.apply() }

        weatherViewModel = WeatherViewModel(weatherUseCase, application)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun `test callWeatherAPI on success for mainWeatherData`() = runTest {
        val mockWeatherData = Weather(
            cityName = "New York",
            tempC = 25.0,
        )

        coEvery {
            weatherUseCase.getCurrentCityInfo(any(), any(), any())
        } returns NetworkResult.Success(mockWeatherData)

        weatherViewModel.uiState.test {
            val viewModel = WeatherViewModel(weatherUseCase, application)
            viewModel.callWeatherAPI("New York", false)
            awaitItem()
            val uiState = viewModel.uiState.first()
            assertEquals(uiState.mainWeatherData, mockWeatherData)
            assertTrue(!uiState.loading)
            assertTrue(uiState.error == null)
        }
    }

    @Test
    fun `test callWeatherAPI on error for mainWeatherData`() = runTest {
        val errorMessage = "Failed to load weather data"

        coEvery {
            weatherUseCase.getCurrentCityInfo(any(), any(), any())
        } returns NetworkResult.Error(message = errorMessage, null, null)

        weatherViewModel.uiState.test {
            val viewModel = WeatherViewModel(weatherUseCase, application)
            viewModel.callWeatherAPI("New York", false)
            awaitItem()
            val uiState = weatherViewModel.uiState.first()
            assertTrue(uiState.mainWeatherData == null)
            assertTrue(uiState.loading)
        }
    }

    @Test
    fun `test saveCityWeatherInfo saves data to SharedPreferences`() = runTest {
        // Given

        val mockWeatherData = Weather(
            cityName = "New York",
            tempC = 25.0,
        )

        // When
        weatherViewModel.saveCityWeatherInfo(mockWeatherData)

        // Then
        verify { sharedPreferencesEditor.putString("CITY_NAME", mockWeatherData.cityName) }
        verify { sharedPreferencesEditor.apply() }
    }

    @Test
    fun `test getSavedCityWeatherInfo returns stored city name`() {
        // Given
        val cityName = "New York"
        every { sharedPreferences.getString("CITY_NAME", null) } returns cityName

        // When
        val savedCityName = weatherViewModel.getSavedCityWeatherInfo()

        // Then
        assertEquals(cityName, savedCityName)
    }
}
