package com.example.weatherforecastapplication.network

import com.example.weatherforecastapplication.model.Current
import com.example.weatherforecastapplication.model.Daily
import com.example.weatherforecastapplication.model.Weather
import com.example.weatherforecastapplication.model.WeatherModel

class RemoteSourceTest(private var weatherModel:WeatherModel ):RemoteSource{


    override suspend fun getWeatherOverNetwork(latitude:String, longitude:String, exclude:String, units:String, language:String,)
    : WeatherModel {
        return weatherModel
    }
}