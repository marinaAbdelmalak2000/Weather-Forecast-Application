package com.example.productmvvm.network

import com.example.weatherforecastapplication.Current
import com.example.weatherforecastapplication.Daily
import com.example.weatherforecastapplication.WeatherModel


interface RemoteSource {
    suspend fun getWeatherOverNetwork(): Daily
}