package com.nooro.data.remote.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class WeatherResponseDTO(
    @SerializedName("current")
    var current: Current = Current(),
    @SerializedName("location")
    var location: Location = Location(),
) : Parcelable {
    @Parcelize
     class Current(
        @SerializedName("air_quality")
        var airQuality: AirQuality = AirQuality(),
        @SerializedName("cloud")
        var cloud: Int = 0,
        @SerializedName("condition")
        var condition: Condition = Condition(),
        @SerializedName("dewpoint_c")
        var dewpointC: Double = 0.0,
        @SerializedName("dewpoint_f")
        var dewpointF: Double = 0.0,
        @SerializedName("feelslike_c")
        var feelslikeC: Double = 0.0,
        @SerializedName("feelslike_f")
        var feelslikeF: Double = 0.0,
        @SerializedName("gust_kph")
        var gustKph: Double = 0.0,
        @SerializedName("gust_mph")
        var gustMph: Double = 0.0,
        @SerializedName("heatindex_c")
        var heatindexC: Double = 0.0,
        @SerializedName("heatindex_f")
        var heatindexF: Double = 0.0,
        @SerializedName("humidity")
        var humidity: Int = 0,
        @SerializedName("is_day")
        var isDay: Int = 0,
        @SerializedName("last_updated")
        var lastUpdated: String = "",
        @SerializedName("last_updated_epoch")
        var lastUpdatedEpoch: Int = 0,
        @SerializedName("precip_in")
        var precipIn: Double = 0.0,
        @SerializedName("precip_mm")
        var precipMm: Double = 0.0,
        @SerializedName("pressure_in")
        var pressureIn: Double = 0.0,
        @SerializedName("pressure_mb")
        var pressureMb: Double = 0.0,
        @SerializedName("temp_c")
        var tempC: Double = 0.0,
        @SerializedName("temp_f")
        var tempF: Double = 0.0,
        @SerializedName("uv")
        var uv: Double = 0.0,
        @SerializedName("vis_km")
        var visKm: Double = 0.0,
        @SerializedName("vis_miles")
        var visMiles: Double = 0.0,
        @SerializedName("wind_degree")
        var windDegree: Int = 0,
        @SerializedName("wind_dir")
        var windDir: String = "",
        @SerializedName("wind_kph")
        var windKph: Double = 0.0,
        @SerializedName("wind_mph")
        var windMph: Double = 0.0,
        @SerializedName("windchill_c")
        var windchillC: Double = 0.0,
        @SerializedName("windchill_f")
        var windchillF: Double = 0.0,
    ) : Parcelable {
        @Parcelize
         class AirQuality(
            @SerializedName("co")
            var co: Double = 0.0,
            @SerializedName("gb-defra-index")
            var gbDefraIndex: Int = 0,
            @SerializedName("no2")
            var no2: Double = 0.0,
            @SerializedName("o3")
            var o3: Double = 0.0,
            @SerializedName("pm10")
            var pm10: Double = 0.0,
            @SerializedName("pm2_5")
            var pm25: Double = 0.0,
            @SerializedName("so2")
            var so2: Double = 0.0,
            @SerializedName("us-epa-index")
            var usEpaIndex: Int = 0,
        ) : Parcelable

        @Parcelize
         class Condition(
            @SerializedName("code")
            var code: Int = 0,
            @SerializedName("icon")
            var icon: String = "",
            @SerializedName("text")
            var text: String = "",
        ) : Parcelable
    }

    @Parcelize
    class Location(
        @SerializedName("country")
        var country: String = "",
        @SerializedName("lat")
        var lat: Double = 0.0,
        @SerializedName("localtime")
        var localtime: String = "",
        @SerializedName("localtime_epoch")
        var localtimeEpoch: Int = 0,
        @SerializedName("lon")
        var lon: Double = 0.0,
        @SerializedName("name")
        var name: String = "",
        @SerializedName("region")
        var region: String = "",
        @SerializedName("tz_id")
        var tzId: String = "",
    ) : Parcelable
}