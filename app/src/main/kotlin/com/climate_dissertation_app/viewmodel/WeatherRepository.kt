package com.climate_dissertation_app.viewmodel

interface WeatherRepository {
    fun findWeatherDetails(latitude: Double, longitude: Double): WeatherDetails
}