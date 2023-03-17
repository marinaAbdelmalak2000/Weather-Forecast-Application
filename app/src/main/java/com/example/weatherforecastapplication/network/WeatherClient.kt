package com.example.productmvvm.network

import com.example.weatherforecastapplication.Current
import com.example.weatherforecastapplication.Daily
import com.example.weatherforecastapplication.WeatherModel


class WeatherClient private constructor():RemoteSource{

    val retrofitService : SimpleService by lazy {
        RetrofitHelper.getInstance().create(SimpleService::class.java)
    }
    override suspend fun getWeatherOverNetwork(): Daily {
       val response = retrofitService.getweather()
        return response
    }
    companion object{
        private var instance:WeatherClient?=null
        fun getInstance():WeatherClient{
            return instance?: synchronized(this){
                val temp = WeatherClient()
                temp
            }
        }
    }

}