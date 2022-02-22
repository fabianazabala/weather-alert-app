package com.climate_dissertation_app.service

import androidx.annotation.DrawableRes
import com.climate_dissertation_app.viewmodel.ClothItem
import com.climate_dissertation_app.viewmodel.WeatherDetails

data class CurrentRecommendation(
    val recommendedClothes: List<ClothItem>,
    val weatherDetails: WeatherDetails,
    @DrawableRes val weatherIconResourceId: Int,
    val recommendedWeatherText: String,
    val recommendedGreetingsText: String
)