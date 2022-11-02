package com.example.myweatherapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.domain.location.LocationTracker
import com.example.myweatherapp.domain.repository.WeatherRepository
import com.example.myweatherapp.domain.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) :ViewModel(){
    var state by mutableStateOf(WeatherStates())
    private set

     fun getWeatherInfo()
    {
        viewModelScope.launch {
            state=state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let {
                location ->
                when(val result=repository.getWeatherData(
                    location.latitude,location.longitude)
                )
                {
                    is Resource.Success ->{
                        state=state.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null)
                    }
                    is Resource.Error ->{
                        state=state.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }

                }
            }?:kotlin.run {
                state=state.copy(
                    isLoading = false,
                    error = "موقعیت شما یافت نشد!مچوز دسترسی به لوکیشن و یا فعالسازی GPS خود را بررسی کنید. "
                )
            }
        }
    }
}