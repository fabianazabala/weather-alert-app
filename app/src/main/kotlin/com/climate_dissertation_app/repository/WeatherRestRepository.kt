package com.climate_dissertation_app.repository

import android.net.Uri
import com.climate_dissertation_app.viewmodel.WeatherRepository
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRestRepository @Inject constructor(private val client: OkHttpClient) :
    WeatherRepository {

    private val ApiKey: String = "1f549bc4a06287175484cf71fc0a6ac0"

    override fun fetchWeatherResponse(latitude: Double, longitude: Double, callback: Callback) =
        Uri.Builder()
            .scheme("https")
            .authority("api.openweathermap.org")
            .appendPath("data")
            .appendPath("2.5")
            .appendPath("weather")
            .appendQueryParameter("lat", latitude.toString())
            .appendQueryParameter("lon", longitude.toString())
            .appendQueryParameter("appid", ApiKey)
            .build()
            .run {
                client.newCall(
                    Request.Builder()
                        .get()
                        .url(this.toString())
                        .build()
                ).enqueue(callback)
            }
}
