package com.example.myweatherapp.presentation

import com.example.myweatherapp.domain.weather.WeatherInfo

data class WeatherStates(
    val weatherInfo: WeatherInfo?=null,
    val isLoading:Boolean=false,
    val error:String?=null
){}
