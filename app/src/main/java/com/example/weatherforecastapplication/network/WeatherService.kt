package com.example.weatherforecastapplication.network
import com.example.weatherforecastapplication.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY="83131d2af170eb79407efcc24412f091"
interface WeatherService {
    @GET("data/3.0/onecall")
    suspend fun getweather(
        @Query("lat") latitude : String,
        @Query("lon") longitude : String,
        @Query("exclude") exclude : String,
        @Query("units") units : String,
        @Query("lang") language : String,
        @Query("appid") apiKey: String = API_KEY
    )  : WeatherModel


}