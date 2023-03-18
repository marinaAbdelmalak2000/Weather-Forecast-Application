package com.example.productmvvm.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.weatherforecastapplication.model.WeatherModel

//@Database(entities = arrayOf(WeatherModel::class), version = 1 )
//abstract class WeatherDataBase : RoomDatabase() {
//    abstract fun getProductDao(): WeatherDao
//    companion object{
//        @Volatile
//        private var INSTANCE: WeatherDataBase? = null
//        fun getInstance (ctx: Context): WeatherDataBase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    ctx.applicationContext, WeatherDataBase::class.java, "Product")
//                    .build()
//                INSTANCE = instance
//// return instance
//                instance }
//        }
//    }
//}