package com.example.productmvvm.model

import com.example.productmvvm.network.RemoteSource
import com.example.weatherforecastapplication.model.WeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class Repository private constructor(var remoteSource: RemoteSource):RepositoryInterface{

    companion object{
        private var instant:Repository?=null
        fun getInstance(remoteSource: RemoteSource):Repository{
            return instant?: synchronized(this){
                val temp = Repository(remoteSource)
                instant=temp
                temp
            }
        }
    }

    override suspend fun getAllProduct(): Flow<WeatherModel>  {
        return flowOf(remoteSource.getWeatherOverNetwork())
        println("///////why Nullllllll!!!!!!!!!!!! ${remoteSource.getWeatherOverNetwork()} weather ${remoteSource.getWeatherOverNetwork()}")
    }




}