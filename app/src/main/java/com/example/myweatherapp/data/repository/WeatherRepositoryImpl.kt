package com.example.myweatherapp.data.repository

import com.example.myweatherapp.data.mappers.toWeatherInfo
import com.example.myweatherapp.data.remote.WeatherApi
import com.example.myweatherapp.domain.repository.WeatherRepository
import com.example.myweatherapp.domain.util.Resource
import com.example.myweatherapp.domain.weather.WeatherInfo
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val api: WeatherApi) : WeatherRepository {
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Resource.Success(data = api.getWeatherData(lat = lat, log = long).toWeatherInfo())
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An error")
        }
    }
}