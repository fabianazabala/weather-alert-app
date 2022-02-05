package com.climate_dissertation_app.viewmodel

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
data class WeatherDetails(
    @JsonProperty("coord")
    val coordinates: Coordinates,
    val weather: List<Weather>,
    val base: String,
    @JsonProperty("main")
    val weatherData: WeatherData,
    val visibility: Int,
    val wind: Wind,
    val clouds: Cloud,
    val dt: Int,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    @JsonProperty("name")
    val cityName: String,
    val cod: Int
) : Serializable

data class Coordinates(
    @JsonProperty("lon")
    val longitude: Double,
    @JsonProperty("lat")
    val latitude: Double
)

data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)

data class WeatherData(
    @JsonProperty("temp")
    val temperature: Double,
    @JsonProperty("feels_like")
    val feelsLike: Double,
    @JsonProperty("temp_min")
    val minimumTemperature: Double,
    @JsonProperty("temp_max")
    val maximumTemperature: Double,
    @JsonIgnore
    val temperatureSymbol: String = "°K",
    val pressure: Int,
    val humidity: Int
) {
    fun currentTemperature() = "$temperature $temperatureSymbol"
}

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double
)

data class Cloud(
    val all: Int,
)

data class Sys(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Int,
    val sunset: Int,
)