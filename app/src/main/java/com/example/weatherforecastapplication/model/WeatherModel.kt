package com.example.weatherforecastapplication.model


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


//////////////////////////////////////////////////////////////////////

//data class WeatherModel(
//    val current: Current,
//    val lat: Double,
//    val lon: Double,
//    val minutely: List<Minutely>,
//    val timezone: String,
//    val timezone_offset: Int
//)
//
//data class Current(
//    val clouds: Int,
//    val dew_point: Double,
//    val dt: Int,
//    val feels_like: Double,
//    val humidity: Int,
//    val pressure: Int,
//    val sunrise: Int,
//    val sunset: Int,
//    val temp: Double,
//    val uvi: Double,
//    val visibility: Int,
//    val weather: List<Weather>,
//    val wind_deg: Int,
//    val wind_gust: Double,
//    val wind_speed: Double
//)
//
//data class Minutely(
//    val dt: Int,
//    val precipitation: Int
//)
//
//data class Weather(
//    val description: String,
//    val icon: String,
//    val id: Int,
//    val main: String
//)

////////////////////////////////////////////////////////////////////////

//data class WeatherModel(
//    val base: String,
//    val clouds: Clouds,
//    val cod: Int,
//    val coord: Coord,
//    val dt: Int,
//    val id: Int,
//    val main: Main,
//    val name: String,
//    val sys: Sys,
//    val timezone: Int,
//    val visibility: Int,
//    val weather: List<Weather>,
//    val wind: Wind
//)
//
//data class Clouds(
//    val all: Int
//)
//
//data class Coord(
//    val lat: Double,
//    val lon: Double
//)
//
//data class Main(
//    val feels_like: Double,
//    val humidity: Int,
//    val pressure: Int,
//    val temp: Double,
//    val temp_max: Double,
//    val temp_min: Double
//)
//
//data class Sys(
//    val country: String,
//    val id: Int,
//    val sunrise: Int,
//    val sunset: Int,
//    val type: Int
//)
//
//data class Weather(
//    val description: String,
//    val icon: String,
//    val id: Int,
//    val main: String
//)
//
//data class Wind(
//    val deg: Int,
//    val speed: Double
//)

//*****************************************************************///////////
////////////////////***********///////////////////***********//////////******




//https://api.openweathermap.org/data/2.5/forecast/daily?q=Cairo&cnt=7&units=metric&appid=a6c087dfd9ef124b958606d98cd316a9
//data class WeatherModel(
//    val city: City,
//    val cnt: Int,
//    val cod: String,
//    val list: List<list>,
//    val message: Double
//)
//
//data class City(
//    val coord: Coord,
//    val country: String,
//    val id: Int,
//    val name: String,
//    val population: Int,
//    val timezone: Int
//)
//
//data class list(
//    val clouds: Int,
//    val deg: Int,
//    val dt: Int,
//    val feels_like: FeelsLike,
//    val gust: Double,
//    val humidity: Int,
//    val pop: Double,
//    val pressure: Int,
//    val speed: Double,
//    val sunrise: Int,
//    val sunset: Int,
//    val temp: Temp,
//    val weather: List<Weather>
//)
//
//data class Coord(
//    val lat: Double,
//    val lon: Double
//)
//
//data class FeelsLike(
//    val day: Double,
//    val eve: Double,
//    val morn: Double,
//    val night: Double
//)
//
//data class Temp(
//    val day: Double,
//    val eve: Double,
//    val max: Double,
//    val min: Double,
//    val morn: Double,
//    val night: Double
//)
//
//data class Weather(
//    val description: String,
//    val icon: String,
//    val id: Int,
//    val main: String
//)


data class WeatherModel(
    val alerts: List<Alert>,
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)



