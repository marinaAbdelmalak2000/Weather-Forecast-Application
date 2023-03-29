package com.example.weatherforecastapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Favourite")
data class Favourite (@PrimaryKey (autoGenerate = true)
                      val id: Int = 0, val CityName:String)

data class FavouriteList(val lon:String?,val lan:String?,val cityName:String?)

