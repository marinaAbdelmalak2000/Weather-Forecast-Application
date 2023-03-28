package com.example.productmvvm.network



import com.example.weatherforecastapplication.model.WeatherModel
interface RemoteSource {
    suspend fun getWeatherOverNetwork(latitude:String,longitude:String,exclude:String,units:String,language:String,): WeatherModel
}