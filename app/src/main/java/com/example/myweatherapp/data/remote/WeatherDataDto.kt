package com.example.myweatherapp.data.remote

import com.squareup.moshi.Json

class WeatherDataDto(
    @field:Json(name = "time")
    val time: List<String>,
    @field:Json(name = "pressure_msl")
    val pressure_msl: List<Double>,
    @field:Json(name = "relativehumidity_2m")
    val relativeHumidity_2m: List<Double>,
    @field:Json(name = "temperature_2m")
    val temperature_2m: List<Double>,
    @field:Json(name = "weathercode")
    val weatherCode: List<Double>,
    @field:Json(name = "windspeed_10m")
    val windSpeed_10m: List<Double>
)

