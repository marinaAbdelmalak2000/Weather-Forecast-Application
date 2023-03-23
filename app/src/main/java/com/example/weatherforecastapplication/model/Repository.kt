package com.example.productmvvm.model

import com.example.productmvvm.db.LocalSource
import com.example.productmvvm.network.RemoteSource
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

    override suspend fun getAllWeatherModel( latitude:Double,longitude:Double,exclude:String,units:String,language:String,): Flow<WeatherModel>  {
        return flowOf(remoteSource.getWeatherOverNetwork(latitude,longitude,exclude,units,language,))
        println("///////why Nullllllll!!!!!!!!!!!! ${remoteSource.getWeatherOverNetwork(latitude,longitude,exclude,units,language,)} " +
                "weather ${remoteSource.getWeatherOverNetwork(latitude,longitude,exclude,units,language,)}")
    }

    override fun getUserSettings(): Setting {
      return  localSource.getUserSettings()
    }


}