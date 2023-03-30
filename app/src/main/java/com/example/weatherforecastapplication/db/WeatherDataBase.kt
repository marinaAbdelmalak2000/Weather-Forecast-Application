package com.example.productmvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherforecastapplication.model.*


@Database(entities = arrayOf(WeatherModel::class,Favourite::class, CityAlarmList::class), version = 13)
@TypeConverters(WeatherModelTypeConverter::class)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun getWeatherDao(): WeatherDao
    companion object{
        @Volatile
        private var INSTANCE: WeatherDataBase? = null
        fun getInstance (ctx: Context): WeatherDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext, WeatherDataBase::class.java, "WeatherModel")
                    .build()
                INSTANCE = instance
// return instance
                instance }
        }
    }
}