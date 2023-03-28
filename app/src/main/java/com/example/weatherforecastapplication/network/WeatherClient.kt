package com.example.productmvvm.network


import com.example.weatherforecastapplication.model.WeatherModel
class WeatherClient private constructor():RemoteSource{

    val retrofitService : WeatherService by lazy {
        RetrofitHelper.getInstance().create(WeatherService::class.java)
    }
    override suspend fun getWeatherOverNetwork( latitude:String,longitude:String,exclude:String,units:String,language:String,): WeatherModel {
       val response = retrofitService.getweather(latitude,longitude,exclude,units,language,)
        println("1111111***/////deeeebug why null ${response}")
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