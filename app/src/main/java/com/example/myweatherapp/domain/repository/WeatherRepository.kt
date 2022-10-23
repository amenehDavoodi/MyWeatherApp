package com.example.myweatherapp.domain.repository

import com.example.myweatherapp.domain.util.Resource
import com.example.myweatherapp.domain.weather.WeatherInfo

interface WeatherRepository {
    suspend fun getWeatherData(lat:Double,long:Double):Resource<WeatherInfo>
}