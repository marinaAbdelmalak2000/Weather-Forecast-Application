package com.example.weatherforecastapplication.alerts.view

import com.example.weatherforecastapplication.model.CityAlarmList

interface OnAlertListener {
    fun deleteAlter(cityAlarmList: CityAlarmList)
}