package com.nooro.data.di

import android.content.Context
import com.nooro.data.remote.api.WeatherService
import com.nooro.data.remote.repository.WeatherRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nooro.data.R
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofitModule = module {

    fun provideGson(): Gson {
        return GsonBuilder().setVersion(1.0).create()
    }

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
    }

    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().run {
            addInterceptor(httpLoggingInterceptor)
            build()
        }
    }

    fun provideRetrofit(factory: Gson, client: OkHttpClient, context: Context): Retrofit {
        return Retrofit.Builder().run {
            baseUrl(context.getString(R.string.apiUrl))
            addConverterFactory(GsonConverterFactory.create(factory))
            client(client)
            build()
        }
    }

    fun provideWeatherService(retrofit: Retrofit): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

    fun provideWeatherRepository(weatherService: WeatherService): WeatherRepository {
        return WeatherRepository(weatherService)
    }

    single { provideGson() }
    single { provideHttpLoggingInterceptor() }
    single { provideHttpClient(get()) }
    single { provideRetrofit(get(), get(), get()) }
    single { provideWeatherRepository(get()) }
    single { provideWeatherService(get()) }
}