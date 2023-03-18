package com.example.weatherforecastapplication.network

import com.example.weatherforecastapplication.model.WeatherModel

sealed class ApiState {
    class Success(val data: WeatherModel):ApiState()
    class Failure(val msg:Throwable):ApiState()
    object Loading:ApiState()

}