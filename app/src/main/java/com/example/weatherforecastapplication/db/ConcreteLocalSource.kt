package com.example.productmvvm.db

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.asLiveData
import com.example.weatherforecastapplication.model.Setting
import com.example.weatherforecastapplication.model.WeatherModel
import kotlinx.coroutines.flow.Flow

class ConcreteLocalSource(context: Context) :LocalSource{

    private val dao:WeatherDao by lazy {
        val db:WeatherDataBase=WeatherDataBase.getInstance(context)
        db.getWeatherDao()
    }
    override suspend fun insertWeatherModel(weatherModel: WeatherModel) {
        dao?.insert(weatherModel)
    }


    override suspend fun getStoredWeatherModel(): Flow<WeatherModel>  {
        return dao.getAll()
    }
   private val sharedPreferences: SharedPreferences = context.getSharedPreferences("LastSetting", Context.MODE_PRIVATE)
    override fun getPrameterSettings(): Setting {
        val languageIndex = sharedPreferences.getInt("LastClickLanguage",0)
        val speedIndex = sharedPreferences.getInt("LastClickSpeed", 0)
        val tempretureIndex = sharedPreferences.getInt("LastClickTemp", 0)
        return Setting(languageIndex,speedIndex,tempretureIndex)
    }

}