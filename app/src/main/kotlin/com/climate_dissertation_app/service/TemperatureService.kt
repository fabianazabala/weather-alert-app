package com.climate_dissertation_app.service

import com.climate_dissertation_app.viewmodel.WeatherData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TemperatureService @Inject constructor() {

    private val kelvinConstant = -273.15

    fun convertToCelsius(weatherData: WeatherData) = weatherData.copy(
        temperature = weatherData.temperature + kelvinConstant,
        feelsLike = weatherData.feelsLike + kelvinConstant,
        maximumTemperature = weatherData.maximumTemperature + kelvinConstant,
        minimumTemperature = weatherData.minimumTemperature + kelvinConstant,
        temperatureSymbol = "°C"
    )

}