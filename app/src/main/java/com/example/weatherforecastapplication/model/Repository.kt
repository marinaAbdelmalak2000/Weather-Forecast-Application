package com.example.productmvvm.model

import android.content.Context
import android.net.ConnectivityManager
import com.example.productmvvm.db.LocalSource
import com.example.productmvvm.network.RemoteSource
import com.example.weatherforecastapplication.model.Setting
import com.example.weatherforecastapplication.model.WeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class Repository private constructor(var remoteSource: RemoteSource,var localSource: LocalSource):RepositoryInterface{

    companion object{
        private var instant:Repository?=null
        fun getInstance(remoteSource: RemoteSource, localSource: LocalSource):Repository{
            return instant?: synchronized(this){
                val temp = Repository(remoteSource,localSource)
                instant=temp
                temp
            }
        }
    }

    override suspend fun getAllWeatherModel( latitude:String,longitude:String,exclude:String,units:String,language:String,): Flow<WeatherModel>  {
        return flowOf(remoteSource.getWeatherOverNetwork(latitude,longitude,exclude,units,language,))
        println("///////why Nullllllll!!!!!!!!!!!! ${remoteSource.getWeatherOverNetwork(latitude,longitude,exclude,units,language,)} " +
                "weather ${remoteSource.getWeatherOverNetwork(latitude,longitude,exclude,units,language,)}")
    }

    override fun getPrameterSettingsLocal(): Setting {
      return  localSource.getPrameterSettings()
    }

    override suspend fun getStoredWeatherModel(): Flow<WeatherModel> {
        return localSource.getStoredWeatherModel()
    }

    override suspend fun insertWeatherModel(weatherModel: WeatherModel) {
        return localSource.insertWeatherModel(weatherModel)
    }

    // Fetch data from network and store it in myData
//    override suspend fun fetchData() {
//        if (isNetworkAvailable()) {
//            // Fetch data from the network
//            getPrameterSettingsLocal()
//        } else {
//            // Fetch data from local storage
//            getStoredWeatherModel()
//        }
//    }
//    private fun isNetworkAvailable(): Boolean {
//        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetworkInfo = connectivityManager.activeNetworkInfo
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected
//    }

}