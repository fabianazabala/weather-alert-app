package com.climate_dissertation_app.service

import com.climate_dissertation_app.R
import com.climate_dissertation_app.repository.ClothesRepository
import com.climate_dissertation_app.viewmodel.*
import java.time.Clock
import java.time.Instant
import java.time.LocalTime
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecommendationService @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val clothesRepository: ClothesRepository,
    private val clock: Clock
) {

    fun recommendClothesBasedOnWeather(latitude: Double, longitude: Double): CurrentRecommendation {
        val weather = weatherRepository.findWeatherDetails(latitude, longitude)
        var recommendedClothes: List<ClothItem> = emptyList()
        var recommendedWeatherIcon: Int =
            if (isNight(weather)) R.drawable.ic_weather_night_partly_cloudy
            else R.drawable.ic_weather_sunny


        if (isRainyDay(weather.rain)) {
            recommendedClothes = clothesRepository.rainyDay()
            recommendedWeatherIcon = R.drawable.ic_weather_pouring
        }

        if (isSnowyDay(weather.snow)) {
            recommendedClothes = clothesRepository.snowyDay()
            recommendedWeatherIcon = R.drawable.ic_weather_snowy_heavy
        }

        if (isColdAndWindyDay(weather.weatherData, weather.wind)) {
            recommendedClothes = clothesRepository.coldAndWindyDay()
        }

        if (isColdDay(weather.weatherData)) {
            recommendedClothes = clothesRepository.coldDay()
            recommendedWeatherIcon = R.drawable.ic_weather_cloudy
        }

        if (isFreshDay(weather.weatherData)) {
            recommendedClothes = clothesRepository.freshDay()
        }

        if (isSportDay(weather.weatherData)) {
            recommendedClothes = clothesRepository.sportsDay()
        }

        if (isWarmDay(weather.weatherData)) {
            recommendedClothes = clothesRepository.warmDay()
        }

        if (isBeachDay(weather.weatherData, weather.wind)) {
            recommendedClothes = clothesRepository.beachDay()
        }

        return CurrentRecommendation(
            recommendedClothes.sortedBy { it.clothPosition },
            weather,
            recommendedWeatherIcon,
            recommendCurrentWeatherText(weather),
            recommendGreetingsText(weather)
        )
    }

    private fun recommendCurrentWeatherText(weatherDetails: WeatherDetails): String =
        StringBuilder()
            .append("Currently there's ${weatherDetails.weatherData.temperature} ${weatherDetails.weatherData.temperatureSymbol}")
            .append("in ${weatherDetails.cityName} but it feels like ${weatherDetails.weatherData.feelsLike} ${weatherDetails.weatherData.temperatureSymbol}")
            .append(System.lineSeparator())
            .append("Today we're expecting a ${weatherSummaryString(weatherDetails)} day!")
            .toString()

    private fun recommendGreetingsText(weatherDetails: WeatherDetails): String =
        StringBuilder()
            .append("Good ")
            .append(if (isNight(weatherDetails)) "Night" else "Day")
            .append(", ")
            .toString()

    private fun weatherSummaryString(weatherDetails: WeatherDetails): String {
        return when {
            isSnowyDay(weatherDetails.snow) -> "snowy"
            isRainyDay(weatherDetails.rain) -> "rainy"
            isWindyDay(weatherDetails.wind) -> "windy"
            isWarmDay(weatherDetails.weatherData) -> "warm"
            else -> "regular"
        }
    }

    private fun isNight(weather: WeatherDetails): Boolean {
        return LocalTime.now(clock).isAfter(
            Instant.ofEpochMilli(weather.sys.sunset).atZone(clock.zone)
                .toLocalTime()
        )
    }

    private fun isColdDay(weatherData: WeatherData) = weatherData.feelsLike <= 15.0

    private fun isWindyDay(wind: Wind) = wind.speed >= 9.0

    private fun isColdAndWindyDay(weatherData: WeatherData, wind: Wind) =
        isColdDay(weatherData) && isWindyDay(wind)

    private fun isFreshDay(weatherData: WeatherData) = weatherData.feelsLike in 15.1..18.0

    private fun isSportDay(weatherData: WeatherData) = weatherData.feelsLike in 18.1..23.0

    private fun isWarmDay(weatherData: WeatherData) = weatherData.feelsLike in 23.1..29.0

    private fun isBeachDay(weatherData: WeatherData, wind: Wind) =
        weatherData.feelsLike >= 29.1 && wind.speed <= 5.0

    private fun isRainyDay(rain: Rain?) = rain != null

    private fun isSnowyDay(snow: Snow?) = snow != null


}