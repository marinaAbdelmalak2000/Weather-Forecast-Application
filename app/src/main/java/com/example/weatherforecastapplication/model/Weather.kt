package com.example.weatherforecastapplication.model

import com.google.gson.annotations.SerializedName

//data class Weather (
//    var id          : Int?    = null,
//    var main        : String? = null,
//    var description : String? = null,
//    var icon        : String? = null
//)
//
////data class Coord (
////    var lon : Double? = null,
////    var lat : Double? = null
////)
//
//
////data class Main (
////
////     var temp      : Double? = null,
////     var feelsLike : Double? = null,
////     var tempMin   : Double? = null,
////     var tempMax   : Double? = null,
////     var pressure  : Int?    = null,
////     var humidity  : Int?    = null
////
////)
//
////data class Wind (
////     var speed : Double? = null,
////     var deg   : Int?    = null
////)
////
////data class Rain (var _1h : Double? = null)
////
////data class Clouds (var all : Int? = null)
////
////data class Sys (
////    var type    : Int?    = null,
////    var id      : Int?    = null,
////    var country : String? = null,
////    var sunrise : Int?    = null,
////    var sunset  : Int?    = null
////)
//
//
//data class Current (
//
//    @SerializedName("dt"         ) var dt         : Int?               = null,
//    @SerializedName("sunrise"    ) var sunrise    : Int?               = null,
//    @SerializedName("sunset"     ) var sunset     : Int?               = null,
//    @SerializedName("temp"       ) var temp       : Double?            = null,
//    @SerializedName("feels_like" ) var feelsLike  : Double?            = null,
//    @SerializedName("pressure"   ) var pressure   : Int?               = null,
//    @SerializedName("humidity"   ) var humidity   : Int?               = null,
//    @SerializedName("dew_point"  ) var dewPoint   : Double?            = null,
//    @SerializedName("uvi"        ) var uvi        : Int?               = null,
//    @SerializedName("clouds"     ) var clouds     : Int?               = null,
//    @SerializedName("visibility" ) var visibility : Int?               = null,
//    @SerializedName("wind_speed" ) var windSpeed  : Double?            = null,
//    @SerializedName("wind_deg"   ) var windDeg    : Int?               = null,
//    @SerializedName("wind_gust"  ) var windGust   : Double?            = null,
//    @SerializedName("weather"    ) var weather    : ArrayList<Weather> = arrayListOf()
//
//)
//
//data class Daily (
//
//    @SerializedName("dt"         ) var dt        : Int?               = null,
//    @SerializedName("sunrise"    ) var sunrise   : Int?               = null,
//    @SerializedName("sunset"     ) var sunset    : Int?               = null,
//    @SerializedName("moonrise"   ) var moonrise  : Int?               = null,
//    @SerializedName("moonset"    ) var moonset   : Int?               = null,
//    @SerializedName("moon_phase" ) var moonPhase : Double?            = null,
//    @SerializedName("temp"       ) var temp      : Temp?              = Temp(),
//    @SerializedName("feels_like" ) var feelsLike : FeelsLike?         = FeelsLike(),
//    @SerializedName("pressure"   ) var pressure  : Int?               = null,
//    @SerializedName("humidity"   ) var humidity  : Int?               = null,
//    @SerializedName("dew_point"  ) var dewPoint  : Double?            = null,
//    @SerializedName("wind_speed" ) var windSpeed : Double?            = null,
//    @SerializedName("wind_deg"   ) var windDeg   : Int?               = null,
//    @SerializedName("wind_gust"  ) var windGust  : Double?            = null,
//    @SerializedName("weather"    ) var weather   : ArrayList<Weather> = arrayListOf(),
//    @SerializedName("clouds"     ) var clouds    : Int?               = null,
//    @SerializedName("pop"        ) var pop       : Double?            = null,
//    @SerializedName("uvi"        ) var uvi       : Double?            = null
//
//)
//
//data class FeelsLike (
//
//     var day   : Double? = null,
//     var night : Double? = null,
//     var eve   : Double? = null,
//     var morn  : Double? = null
//
//)
//
//data class Hourly (
//
//     var dt         : Int?               = null,
//     var temp       : Double?            = null,
//     var feelsLike  : Double?            = null,
//     var pressure   : Int?               = null,
//     var humidity   : Int?               = null,
//     var dewPoint   : Double?            = null,
//     var uvi        : Int?               = null,
//     var clouds     : Int?               = null,
//     var visibility : Int?               = null,
//     var windSpeed  : Double?            = null,
//     var windDeg    : Int?               = null,
//     var windGust   : Double?            = null,
//     var weather    : ArrayList<Weather> = arrayListOf(),
//     var pop        : Int?               = null
//
//)
//
//data class Minutely (
//
//     var dt            : Int? = null,
//     var precipitation : Int? = null
//
//)
//
//data class Temp (
//
//     var day   : Double? = null,
//     var min   : Double? = null,
//     var max   : Double? = null,
//     var night : Double? = null,
//     var eve   : Double? = null,
//     var morn  : Double? = null
//
//)
//
//data class WeatherModel (
//
//     var lat            : Double?             = null,
//     var lon            : Double?             = null,
//     var timezone       : String?             = null,
//     var timezoneOffset : Int?                = null,
//     var current        : Current?            = Current(),
//     var minutely       : ArrayList<Minutely> = arrayListOf(),
//     var hourly         : ArrayList<Hourly>   = arrayListOf(),
//     var daily          : ArrayList<Daily>    = arrayListOf()
//
//)
//
////data class WeatherModel (
////
////    var coord      : Coord?             = Coord(),
////    var weather    : ArrayList<Weather> = arrayListOf(),
////    var base       : String?            = null,
////    var main       : Main?              = Main(),
////    var visibility : Int?               = null,
////    var wind       : Wind?              = Wind(),
////    var rain       : Rain?              = Rain(),
////    var clouds     : Clouds?            = Clouds(),
////    var dt         : Int?               = null,
////    var sys        : Sys?               = Sys(),
////    var timezone   : Int?               = null,
////    var id         : Int?               = null,
////    var name       : String?            = null,
////    var cod        : Int?               = null
////
////)
