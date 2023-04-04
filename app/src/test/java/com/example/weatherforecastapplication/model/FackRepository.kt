package com.example.weatherforecastapplication.model

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.junit.Assert.*

class FackRepository(private var favoriteList: MutableList<Favourite> = mutableListOf<Favourite>(),
                     private var alertList: MutableList<CityAlarmList> = mutableListOf<CityAlarmList>(),
                     private var weatherModelNetwork: WeatherModel = WeatherModel(0,
                         current =  Current(0,0.0,0,0.0,0,0,0,0,
                             0.0,0.0,0, weather = emptyList(),0,0.0),
                         lon = 52.2,
                         lat = 45.0,
                         timezone_offset = 55,
                         timezone = "Africe/Cairo",
                         hourly = emptyList(),
                         daily = emptyList(), alerts = Alert("description",0,"event","stander",0,emptyList())
                     )) : RepositoryInterface{


    override suspend fun getAllWeatherModel(
        latitude: String,
        longitude: String,
        exclude: String,
        units: String,
        language: String
    ): Flow<WeatherModel> = flow {
        emit(weatherModelNetwork)
    }

    override fun getPrameterSettingsLocal(): Setting {
        TODO("Not yet implemented")
    }

    override suspend fun getStoredWeatherModel(): Flow<WeatherModel> {
        TODO("Not yet implemented")
    }

    override suspend fun insertWeatherModel(weatherModel: WeatherModel) {
        TODO("Not yet implemented")
    }

    override suspend fun getStoredFavourite(): Flow<List<Favourite>> = flow{
        emit(favoriteList)
    }

    override suspend fun insertFavourite(favouriteCity: Favourite) {
        favoriteList.add(favouriteCity)
    }

    override suspend fun deleteFavourite(favouriteCity: Favourite) {
        favoriteList.remove(favouriteCity)
    }

    override suspend fun getAlerts(): Flow<List<CityAlarmList>> = flow{
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