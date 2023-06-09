package com.example.weatherforecastapplication.network


import android.content.Context
import android.content.SharedPreferences
import com.example.weatherforecastapplication.model.CityAlarmList
import com.example.weatherforecastapplication.model.Favourite
import com.example.weatherforecastapplication.model.Setting
import com.example.weatherforecastapplication.model.WeatherModel
import kotlinx.coroutines.flow.Flow

class ConcreteLocalSource(context: Context) :LocalSource{

    private val dao:WeatherDao by lazy {
        val db:WeatherDataBase=WeatherDataBase.getInstance(context)
        db.getWeatherDao()
    }
    override suspend fun insertWeatherModel(weatherModel: WeatherModel) {
        dao.insert(weatherModel)
    }


    override suspend fun getStoredWeatherModel(): Flow<WeatherModel>  {
        return dao.getAll()
    }
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("LastSetting", Context.MODE_PRIVATE)
    //LastLocation
    private val sharedLocation: SharedPreferences = context.getSharedPreferences("LastLocation", Context.MODE_PRIVATE)
    override fun getPrameterSettings(): Setting {
        val languageIndex = sharedPreferences.getInt("LastClickLanguage",0)
        val speedIndex = sharedPreferences.getInt("LastClickSpeed", 0)
        val tempretureIndex = sharedPreferences.getInt("LastClickTemp", 0)
        val locationIndex = sharedPreferences.getInt("LastClickLocation",0)
        val longitude=sharedLocation.getString("longitude"," ")
        val latitude=sharedLocation.getString("latitude","30.25")
        return Setting(languageIndex,speedIndex,tempretureIndex,locationIndex,longitude,latitude)
    }

    override suspend fun insertFavourite(favouriteCity: Favourite) {
        return dao.insertfavourite(favouriteCity)
    }

    override suspend fun getStoredFavourite(): Flow<List<Favourite>> {
        return dao.getAllFavourite()
    }
    override suspend fun deleteFavourite(favouriteCity: Favourite) {
        return dao.deletefavourite(favouriteCity)
    }

    override suspend fun getAlerts(): Flow<List<CityAlarmList>> {
        return dao.getAlerts()
    }

    override suspend fun insertAlert(alert: CityAlarmList): Long {
        return dao.insertAlert(alert)
    }

    override suspend fun deleteAlert(id: Int) {
        return dao.deleteAlert(id)
    }

    override suspend fun getOneAlert(id: Int): CityAlarmList{
        return dao.getOneAlert(id)
    }

}