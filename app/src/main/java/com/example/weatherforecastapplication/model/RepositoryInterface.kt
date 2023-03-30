package com.example.productmvvm.model

import com.example.weatherforecastapplication.model.Favourite

import com.example.weatherforecastapplication.model.Setting
import com.example.weatherforecastapplication.model.WeatherModel
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun getAllWeatherModel( latitude:String,longitude:String,exclude:String,units:String,language:String,): Flow<WeatherModel>
    fun getPrameterSettingsLocal(): Setting
    suspend fun getStoredWeatherModel(): Flow<WeatherModel>
    suspend fun insertWeatherModel(weatherModel: WeatherModel)

    suspend fun getStoredFavourite(): Flow<List<Favourite>>
    suspend fun insertFavourite(favouriteCity: Favourite)
   // fun getPrameterFavouriteList(): FavouriteList
    suspend fun deleteFavourite(favouriteCity: Favourite)

}