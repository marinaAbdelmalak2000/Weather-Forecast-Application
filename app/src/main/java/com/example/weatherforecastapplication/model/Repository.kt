package com.example.productmvvm.model

import com.example.productmvvm.network.RemoteSource
import com.example.weatherforecastapplication.Current
import com.example.weatherforecastapplication.Daily
import com.example.weatherforecastapplication.WeatherModel

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

    override suspend fun getAllProduct(): Daily {
        return remoteSource.getWeatherOverNetwork()
    }




}