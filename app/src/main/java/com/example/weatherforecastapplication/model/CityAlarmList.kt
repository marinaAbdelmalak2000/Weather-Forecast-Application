package com.example.weatherforecastapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CityAlarmList")
data class CityAlarmList (@PrimaryKey (autoGenerate = true)
                          val id:Int=0,val CityName:String,val lon:String?,val lan:String?)