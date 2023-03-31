package com.example.productmvvm.model

import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import com.example.productmvvm.db.LocalSource
import com.example.productmvvm.network.RemoteSource
import com.example.weatherforecastapplication.model.CityAlarmList
import com.example.weatherforecastapplication.model.Favourite

import com.example.weatherforecastapplication.model.Setting
import com.example.weatherforecastapplication.model.WeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class Repository private constructor(var remoteSource: RemoteSource,var localSource: LocalSource):RepositoryInterface{

    companion object{
        private var instant:Repository?=null
        fun getInstance(remoteSource: RemoteSource, localSource: LocalSource):Repository{
            return instant?: synchronized(this){
                val temp = Repository(remoteSource,localSource)
                instant=temp
                temp
            }
        }
    }

    override suspend fun getAllWeatherModel( latitude:String,longitude:String,exclude:String,units:String,language:String,): Flow<WeatherModel>  {
        return flowOf(remoteSource.getWeatherOverNetwork(latitude,longitude,exclude,units,language,))
        println("///////why Nullllllll!!!!!!!!!!!! ${remoteSource.getWeatherOverNetwork(latitude,longitude,exclude,units,language,)} " +
                "weather ${remoteSource.getWeatherOverNetwork(latitude,longitude,exclude,units,language,)}")
    }

    override fun getPrameterSettingsLocal(): Setting {
      return  localSource.getPrameterSettings()
    }

    override suspend fun getStoredWeatherModel(): Flow<WeatherModel> {
        return localSource.getStoredWeatherModel()
    }

    override suspend fun insertWeatherModel(weatherModel: WeatherModel) {
        return localSource.insertWeatherModel(weatherModel)
    }

    override suspend fun getStoredFavourite(): Flow<List<Favourite>> {
        return localSource.getStoredFavourite()
    }

    override suspend fun insertFavourite(favouriteCity: Favourite) {
        return localSource.insertFavourite(favouriteCity)

    }

//    override fun getPrameterFavouriteList(): FavouriteList {
//        return localSource.getPrameterFavouriteList()
//        Log.i(TAG, " deeeeeeeeeebug localSource.getPrameterFavouriteList()::: ${localSource.getPrameterFavouriteList().cityName} ")
//    }

    override suspend fun deleteFavourite(favouriteCity: Favourite) {
        return localSource.deleteFavourite(favouriteCity)

    }

    override suspend fun getAlerts(): Flow<List<CityAlarmList>> {
        return localSource.getAlerts()
    }

    override suspend fun insertAlert(alert: CityAlarmList): Long {
        return localSource.insertAlert(alert)
    }

    override suspend fun deleteAlert(id: Int) {
        return localSource.deleteAlert(id)
    }


}