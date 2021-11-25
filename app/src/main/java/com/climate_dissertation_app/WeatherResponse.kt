package com.climate_dissertation_app

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class WeatherResponse(
    @JsonProperty("coord")
    val coordinates: Coordinates,
    val weather: List<Weather>
)

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