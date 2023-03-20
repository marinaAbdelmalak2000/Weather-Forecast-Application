package com.example.weatherforecastapplication.model

data class Hourly(
    val clouds: Int,
    val dew_point: Double,
    val dt: Long,
    val feels_like: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val temp: Double,
    val uvi: Double,
    val visibility: Int,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_gust: Double,
    val wind_speed: Double
)

//fun getHourly()= listOf<Hourly>(
//    Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0),
//    Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0),
//    Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0),
//    Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0),
//    Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0),
//    Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0),
//    Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0)
//    ,Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0)
//    ,Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0)
//,Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0)
//,Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0)
//,Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0)
//,Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0)
//    ,Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0)
//    ,Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0)
//    ,Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0)
//    ,Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0)
//    ,Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0)
//    ,Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0)
//    ,Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0)
//    ,Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0)
//    ,Hourly(0,0.0,1678816878,2.0,44,1,20,27.0,2.0,2
//        , listOf( Weather("des","hjy",1,"main")),1,2.0,2.0)
//   )