package com.example.productmvvm.network



import com.example.weatherforecastapplication.model.WeatherModel
import retrofit2.http.GET

interface WeatherService {

    //   https://api.openweathermap.org/data/2.5/weather?q=London&appid=83131d2af170eb79407efcc24412f091

    //  https://api.openweathermap.org/data/3.0/onecall?lat=33.44&lon=-94.04&appid=83131d2af170eb79407efcc24412f091

    //  https://api.openweathermap.org/data/3.0/onecall?lat=33.44&lon=-94.04&appid=83131d2af170eb79407efcc24412f091

    // data/2.5/onecall?lat=33.44&lon=-94.04&exclude=hourly,daily&appid=83131d2af170eb79407efcc24412f091

    // https://api.openweathermap.org/data/3.0/onecall?lat=33.0&lon=-94.0&appid=83131d2af170eb79407efcc24412f091

    // https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&exclude=hourly,daily&appid=83131d2af170eb79407efcc24412f091

    // data/2.5/onecall?lat=33.44&lon=-94.04&exclude=hourly,daily&appid=b954c9780cc449a9d87442cc790cd7cf


    //https://api.openweathermap.org/data/3.0/onecall?lat=30.0&lon=30.0&exclude=minutely&appid=83131d2af170eb79407efcc24412f091

    //https://api.openweathermap.org/data/3.0/onecall?lat=33.44&lon=-94.04&units=metric,standard,imperial&lang=ar&exclude=minutely&appid=83131d2af170eb79407efcc24412f091


    ///////////////////////////****////////*****////////*****/////////*****///////////

//    @GET("data/3.0/onecall?lat=33.44&lon=-94.04&units=metric,standard,imperial&lang=ar&exclude=minutely&appid=83131d2af170eb79407efcc24412f091")
//    suspend fun getweather()  : WeatherModel

}