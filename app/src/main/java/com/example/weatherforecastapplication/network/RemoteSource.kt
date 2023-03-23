package com.example.productmvvm.network



import com.example.weatherforecastapplication.model.WeatherModel
interface RemoteSource {
    suspend fun getWeatherOverNetwork(latitude:Double,longitude:Double,exclude:String,units:String,language:String,): WeatherModel
}