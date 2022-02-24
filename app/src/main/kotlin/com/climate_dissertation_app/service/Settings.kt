package com.climate_dissertation_app.service

import java.time.temporal.ChronoUnit

data class Settings(
    val amount: Int,
    val unit: ChronoUnit
)