package com.example.weatherforecastapplication.model

import androidx.room.Entity

data class Alert(
    val description: String,
    val end: Int,
    val event: String,
    val sender_name: String,
    val start: Int,
    val tags: List<String>
)

@Entity(tableName = "Alarm")
data class Alarm( var id :Int,var date :String,val city:String,var long:String,var lang:String)