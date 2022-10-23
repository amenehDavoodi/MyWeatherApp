package com.example.myweatherapp.data.mappers

import com.example.myweatherapp.data.remote.WeatherDataDto
import com.example.myweatherapp.data.remote.WeatherDto
import com.example.myweatherapp.domain.weather.WeatherData
import com.example.myweatherapp.domain.weather.WeatherInfo
import com.example.myweatherapp.domain.weather.WeatherType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun WeatherDataDto.toWeatherDataMap(): Map<Int, List<WeatherData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperature_2m[index]
        val weatherCode = weatherCode[index]
        val windSpeed = windSpeed_10m[index]
        val pressure = pressure_msl[index]
        val humidity = relativeHumidity_2m[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                tempCel = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode.toInt())
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues { it ->
        it.value.map { it.data }
    }
//        .also { it.entries.size }


}
private data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)
fun WeatherDto.toWeatherInfo():WeatherInfo
{
    val weatherDataMap=weatherData.toWeatherDataMap()
    val now=LocalDateTime.now()
    val currentWeatherData=weatherDataMap[0]?.find {
        val hour=if (now.minute<30) now.hour else now.hour+1
        it.time.hour == hour
    }
    return WeatherInfo(weatherDataPerDay = weatherDataMap,currentWeatherData=currentWeatherData)
}