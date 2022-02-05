package com.climate_dissertation_app.viewmodel

import okhttp3.Callback

interface WeatherRepository {
    fun fetchWeatherResponse(latitude: Double, longitude: Double, callback: Callback)
}