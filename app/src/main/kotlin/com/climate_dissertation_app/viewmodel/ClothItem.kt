package com.climate_dissertation_app.viewmodel

import androidx.annotation.DrawableRes

data class ClothItem(
    @DrawableRes val resourceId: Int,
    val name: String
)