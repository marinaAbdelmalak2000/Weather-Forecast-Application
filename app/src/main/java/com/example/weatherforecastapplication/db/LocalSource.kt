package com.example.productmvvm.db


import com.example.weatherforecastapplication.model.Favourite
import com.example.weatherforecastapplication.model.FavouriteList
import com.example.weatherforecastapplication.model.Setting
import com.example.weatherforecastapplication.model.WeatherModel
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    suspend fun insertWeatherModel(weatherModel: WeatherModel)
    suspend fun getStoredWeatherModel(): Flow<WeatherModel>

    fun getPrameterSettings(): Setting

    suspend fun insertFavourite(favouriteCity: Favourite)
    suspend fun getStoredFavourite(): Flow<List<Favourite>>

    fun getPrameterFavouriteList(): FavouriteList

}