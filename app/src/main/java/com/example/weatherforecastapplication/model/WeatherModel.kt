package com.example.weatherforecastapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


@Entity(tableName = "WeatherModel")
data class WeatherModel(
    @PrimaryKey
    val id: Int = 0,
    val alerts: Alert,
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Int
)


class WeatherModelTypeConverter {
    @TypeConverter
    fun fromCurrent(current: Current?): String {
        val gson = Gson()
        return gson.toJson(current)
    }

    @TypeConverter
    fun toCurrent(json: String?): Current {
        val gson = Gson()
        return gson.fromJson(json, Current::class.java)
    }
    @TypeConverter
    fun fromAlert(alert: Alert?): String {
        val gson = Gson()
        return gson.toJson(alert)
    }

    @TypeConverter
    fun toAlert(json: String?): Alert {
        val gson = Gson()
        return gson.fromJson(json, Alert::class.java)
    }

    @TypeConverter
    fun fromDaily(dailyList: List<Daily?>?): String? {
        val gson = Gson()
        return gson.toJson(dailyList)
    }
    @TypeConverter
    fun ToDailyList(data: String?): List<Daily?>? {
        val gson = Gson()
        val listType: Type = object : TypeToken<List<Daily?>?>() {}.type
        return gson.fromJson(data, listType)
    }
    @TypeConverter
    fun hourlyListToString(hourlyList: List<Hourly?>?): String? {
        val gson = Gson()
        return gson.toJson(hourlyList)
    }
    @TypeConverter
    fun stringToHourlyList(data: String?): List<Hourly?>? {
        val gson = Gson()
        val listType = object : TypeToken<List<Hourly?>?>() {}.type
        return gson.fromJson(data, listType)
    }
}




