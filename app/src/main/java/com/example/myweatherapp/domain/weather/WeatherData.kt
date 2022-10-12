package com.example.myweatherapp.domain.weather

import java.time.LocalDateTime

data class WeatherData(
    val time:LocalDateTime,
    val tempCel:Double,
    val pressure:Double,
    val windSpeed:Double,
    val humidity:Double,
    val weatherType: WeatherType
)
