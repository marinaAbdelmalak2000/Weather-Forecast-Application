package com.example.weatherforecastapplication.model

import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun getAllWeatherModel( latitude:String,longitude:String,exclude:String,units:String,language:String,): Flow<WeatherModel>
    fun getPrameterSettingsLocal(): Setting
    suspend fun getStoredWeatherModel(): Flow<WeatherModel>
    suspend fun insertWeatherModel(weatherModel: WeatherModel)

    suspend fun getStoredFavourite(): Flow<List<Favourite>>
    suspend fun insertFavourite(favouriteCity: Favourite)
    suspend fun deleteFavourite(favouriteCity: Favourite)

    suspend fun getAlerts():Flow<List<CityAlarmList>>
    suspend fun insertAlert(alert: CityAlarmList) : Long
    suspend fun deleteAlert(id:Int)

    suspend fun getOneAlert(id: Int):CityAlarmList

}