package com.example.weatherforecastapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favourite")
data class Favourite (@PrimaryKey
                      val CityName:String,val lon:String?,val lan:String?)



