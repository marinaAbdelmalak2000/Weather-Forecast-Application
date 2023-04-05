package com.example.weatherforecastapplication.network



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

    suspend fun deleteFavourite(favouriteCity: Favourite)

    //alert
    suspend fun getAlerts():Flow<List<CityAlarmList>>
    suspend fun insertAlert(alert: CityAlarmList) : Long
    suspend fun deleteAlert(id:Int)
    suspend fun getOneAlert(id: Int):CityAlarmList

}