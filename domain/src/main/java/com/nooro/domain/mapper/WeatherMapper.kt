package com.nooro.domain.mapper

import com.nooro.data.remote.model.WeatherResponseDTO
import com.nooro.domain.model.Weather

class WeatherMapper {

    fun map(dto: WeatherResponseDTO): Weather {
        return Weather(
            cityName = dto.location.name,
            feelsLikeC = dto.current.feelslikeC,
            humidity = dto.current.humidity,
            uv = dto.current.uv,
            icon = dto.current.condition.icon,
            tempC = dto.current.tempC,


//            current = mapCurrent(dto.current),
//            location = mapLocation(dto.location),
        )
    }

//    private fun mapCurrent(dto: WeatherResponseDTO.Current): Weather.Current {
//        return Weather.Current(
//            airQuality = mapAirQuality(dto.airQuality),
//            cloud = dto.cloud,
//            condition = mapCondition(dto.condition),
//            dewpointC = dto.dewpointC,
//            dewpointF = dto.dewpointF,
//            feelslikeC = dto.feelslikeC,
//            feelslikeF = dto.feelslikeF,
//            gustKph = dto.gustKph,
//            gustMph = dto.gustMph,
//            heatindexC = dto.heatindexC,
//            heatindexF = dto.heatindexF,
//            humidity = dto.humidity,
//            isDay = dto.isDay,
//            lastUpdated = dto.lastUpdated,
//            lastUpdatedEpoch = dto.lastUpdatedEpoch,
//            precipIn = dto.precipIn,
//            precipMm = dto.precipMm,
//            pressureIn = dto.pressureIn,
//            pressureMb = dto.pressureMb,
//            tempC = dto.tempC,
//            tempF = dto.tempF,
//            uv = dto.uv,
//            visKm = dto.visKm,
//            visMiles = dto.visMiles,
//            windDegree = dto.windDegree,
//            windDir = dto.windDir,
//            windKph = dto.windKph,
//            windMph = dto.windMph,
//            windchillC = dto.windchillC,
//            windchillF = dto.windchillF,
//        )
//    }
//
//    private fun mapLocation(dto: WeatherResponseDTO.Location): Weather.Location {
//        return Weather.Location(
//            country = dto.country,
//            lat = dto.lat,
//            localtime = dto.localtime,
//            localtimeEpoch = dto.localtimeEpoch,
//            lon = dto.lon,
//            name = dto.name,
//            region = dto.region,
//            tzId = dto.tzId,
//        )
//    }
//
//    private fun mapAirQuality(dto: WeatherResponseDTO.Current.AirQuality): Weather.Current.AirQuality {
//        return Weather.Current.AirQuality(
//            co = dto.co,
//            gbDefraIndex = dto.gbDefraIndex,
//            no2 = dto.no2,
//            o3 = dto.o3,
//            pm10 = dto.pm10,
//            pm25 = dto.pm25,
//            so2 = dto.so2,
//            usEpaIndex = dto.usEpaIndex,
//        )
//    }
//
//    private fun mapCondition(dto: WeatherResponseDTO.Current.Condition): Condition {
//        return Condition(
//            code = dto.code,
//            icon = dto.icon,
//            text = dto.text,
//        )
//    }
}