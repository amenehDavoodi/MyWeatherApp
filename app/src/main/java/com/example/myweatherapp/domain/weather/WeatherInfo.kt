package com.example.myweatherapp.domain.weather

import com.example.myweatherapp.data.remote.WeatherDataDto

data class WeatherInfo(
    val weatherDataPerDay:Map<Int,List<WeatherData>>
    ,val currentWeatherData:WeatherData?
)
