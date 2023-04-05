package com.example.weatherforecastapplication.network

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.example.weatherforecastapplication.model.CityAlarmList
import com.example.weatherforecastapplication.model.Favourite
import com.example.weatherforecastapplication.model.WeatherModel


sealed class ApiState {

    class Success(val data: WeatherModel) : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    object Loading : ApiState()

    class SuccessFavourite(val data: List<Favourite>) : ApiState()
    class SuccessAlert(val data: List<CityAlarmList>) : ApiState()
    class SuccessOneAlert(val data: CityAlarmList) : ApiState()

// class Success<T>(val data: T):ApiState()
}

