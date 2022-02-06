package com.climate_dissertation_app.repository

import android.net.Uri
import com.climate_dissertation_app.service.TemperatureService
import com.climate_dissertation_app.viewmodel.WeatherDetails
import com.climate_dissertation_app.viewmodel.WeatherRepository
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRestRepository @Inject constructor(
    private val client: OkHttpClient,
    private val temperatureService: TemperatureService,
    private val objectMapper: ObjectMapper
) :
    WeatherRepository {

    private val ApiKey: String = "1f549bc4a06287175484cf71fc0a6ac0"

    override fun findWeatherDetails(
        latitude: Double,
        longitude: Double
    ): WeatherDetails = buildWeatherRequestUri(latitude, longitude).let { uri ->
        client.newCall(
            Request.Builder()
                .get()
                .url(uri.toString())
                .build()
        ).execute().let { response -> enrich(mapToWeatherDetails(response)) }
    }

    private fun enrich(weatherDetails: WeatherDetails) =
        weatherDetails.copy(
            weatherData = temperatureService.convertToCelsius(weatherDetails.weatherData),
            weather = weatherDetails.weather.map { weather ->
                weather.copy(iconPicture = findWeatherIcon(weather.icon))
            }
        )


    private fun mapToWeatherDetails(response: Response) = objectMapper.readValue(
        response.body()?.string()!!,
        WeatherDetails::class.java
    )

    private fun buildWeatherRequestUri(
        latitude: Double,
        longitude: Double
    ) = Uri.Builder()
        .scheme("https")
        .authority("api.openweathermap.org")
        .appendPath("data")
        .appendPath("2.5")
        .appendPath("weather")
        .appendQueryParameter("lat", latitude.toString())
        .appendQueryParameter("lon", longitude.toString())
        .appendQueryParameter("appid", ApiKey)
        .build()

    private fun findWeatherIcon(iconId: String) = Uri.Builder()
        .scheme("https")
        .authority("openweathermap.org")
        .appendPath("img")
        .appendPath("w")
        .appendPath("$iconId.png")
        .build()
        .let {
            client.newCall(Request.Builder().get().url(it.toString()).build())
                .execute().body()?.string() ?: throw RuntimeException("No body")
        }
}