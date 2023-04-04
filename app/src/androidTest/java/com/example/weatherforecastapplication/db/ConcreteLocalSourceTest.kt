package com.example.weatherforecastapplication.db

import com.example.weatherforecastapplication.model.*
import com.example.weatherforecastapplication.network.LocalSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import org.junit.Assert.*

import org.junit.Test

class ConcreteLocalSourceTest (private var favoriteList: MutableList<Favourite> = mutableListOf<Favourite>(),
                               private var alertList: MutableList<CityAlarmList> = mutableListOf<CityAlarmList>(),
                               private var weatherModel:WeatherModel=WeatherModel(0, alerts = null,
                                   current = Current(0,0.0,0,0.0,0,0,0,0,
                                       0.0,0.0,0, weather = emptyList(),0,0.0),
                                   daily = emptyList() , hourly = emptyList(), lat =0.0, lon =0.0, timezone = "", timezone_offset = 0))
    :LocalSource{




    @Test
    override suspend fun insertWeatherModel(weatherModel: WeatherModel) {
      //  weatherList.add(weatherModel)
    }

    override suspend fun getStoredWeatherModel(): Flow<WeatherModel> =flow{
      //  emit(weatherList)
    }

    override fun getPrameterSettings(): Setting {
        TODO("Not yet implemented")
    }

    override suspend fun insertFavourite(favouriteCity: Favourite) {
        favoriteList.add(favouriteCity)
    }

    override suspend fun getStoredFavourite(): Flow<List<Favourite>> =flow{
        emit(favoriteList)
    }

    override suspend fun deleteFavourite(favouriteCity: Favourite) {
        favoriteList.remove(favouriteCity)
    }

    override suspend fun getAlerts(): Flow<List<CityAlarmList>> =flow{
        emit(alertList)
    }

    override suspend fun insertAlert(alert: CityAlarmList): Long {
        alertList.add(alert)
        return 0
    }

    override suspend fun deleteAlert(id: Int) {
        alertList.removeAt(id)
    }

    override suspend fun getOneAlert(id: Int): CityAlarmList {
        TODO("Not yet implemented")
    }


}