package com.example.weatherforecastapplication.model

import com.example.weatherforecastapplication.R

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

//fun getIcin(iconApi:String): String {
//    when(iconApi){
//        "01d","01n" -> return "img"     //clear sky
//        "13d","13n" -> return "img_9"   //few clouds
//        "03d","03n" -> return "img_10"  //scattered clouds
//        "04d","04n" -> return "img_7"   //broken clouds
//        "09d","09n" -> return "img_16"  //shower rain
//        "10d","10n" -> return "img_12"  //rain
//        "11d","11n" -> return "img_6"   //thunderstorm
//        "11d","11n" -> return "img_17"  //snow
//        else->iconApi                   //mist
//
//    }
//   return iconApi
//}

//icon===> https://openweathermap.org/img/wn/10d@2x.png
