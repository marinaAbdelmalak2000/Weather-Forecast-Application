package com.example.weatherforecastapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CityAlarmList")
data class CityAlarmList (@PrimaryKey (autoGenerate = true)
                          val id:Int=0,var startTime:Long?=null,var endTime:Long?=null,var startDate:Long?=null,var endDate:Long?=null )