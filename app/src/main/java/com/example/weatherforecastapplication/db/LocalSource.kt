package com.example.productmvvm.db


import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherforecastapplication.model.CityAlarmList
import com.example.weatherforecastapplication.model.Favourite

import com.example.weatherforecastapplication.model.Setting
import com.example.weatherforecastapplication.model.WeatherModel
import kotlinx.coroutines.flow.Flow

interface LocalSource {
    suspend fun insertWeatherModel(weatherModel: WeatherModel)
    suspend fun getStoredWeatherModel(): Flow<WeatherModel>

    fun getPrameterSettings(): Setting

    suspend fun insertFavourite(favouriteCity: Favourite)
    suspend fun getStoredFavourite(): Flow<List<Favourite>>

   // fun getPrameterFavouriteList(): FavouriteList
    suspend fun deleteFavourite(favouriteCity: Favourite)

    //alert
    suspend fun getAlerts():Flow<List<CityAlarmList>>
    suspend fun insertAlert(alert: CityAlarmList) : Long
    suspend fun deleteAlert(id:Int)

}