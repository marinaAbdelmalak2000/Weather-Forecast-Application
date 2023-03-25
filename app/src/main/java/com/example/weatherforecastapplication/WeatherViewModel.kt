package com.example.weatherforecastapplication

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmvvm.model.RepositoryInterface
import com.example.weatherforecastapplication.model.WeatherModel

import com.example.weatherforecastapplication.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class WeatherViewModel (private val _irepo: RepositoryInterface): ViewModel(){

    private val _uiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val uiState = _uiState.asStateFlow()


//    lateinit var units :String
//    lateinit var exclude:String

    val language=_irepo.getPrameterSettingsLocal().getLanguage()
    val unit=_irepo.getPrameterSettingsLocal().getUnit()
//    var checkSpeed =_irepo.getPrameterSettingsLocal().CheckconvertSpeed()
//    var checkTemp=_irepo.getPrameterSettingsLocal().CheckconvertTemp()
    var checkTemp =_irepo.getPrameterSettingsLocal().convertCelToSpacificTemp()
    var checkSpeed=_irepo.getPrameterSettingsLocal().convertMeterPerSecToMilesPerHour()



    init {

        Log.i(TAG, "language::::::::::::::::::::::::::::::::: $language  Index:::::")
      //  Log.i(TAG, " ////language : $language units $units exclude $exclude ")
        allWeatherNetwork(33.44,-94.04,"minutely", unit,language)

    }

    fun allWeatherNetwork(latitude:Double,longitude:Double,exclude:String,units:String,language:String,){
        viewModelScope.launch (Dispatchers.IO){

            _irepo.getAllWeatherModel(latitude,longitude,exclude,units,language,).catch {
                    e->_uiState.value=ApiState.Failure(e)
            }
                .collect{
                        data ->
                    Log.i(TAG, "allWeatherNetwork checkSpeed checkSpeedcheckSpeedcheckSpeed:== $checkSpeed  check temp: $checkTemp")
                    _uiState.value=ApiState.Success(data)
                    insertWeatherModel(data)
                    Log.i(TAG, "allWeatherNetwork: ${data.current.wind_speed}")
                    Log.i(TAG, "insertWeatherModel(data): ${insertWeatherModel(data)}")
                }

            }
        }

    fun insertWeatherModel(weatherModel: WeatherModel){
        viewModelScope.launch (Dispatchers.IO){
            _irepo.insertWeatherModel(weatherModel)
        }
    }

    fun getLocalProduct(){
        viewModelScope.launch (Dispatchers.IO){

            _irepo.getStoredWeatherModel().catch {
                    e->_uiState.value=ApiState.Failure(e)
            }
                .collect{
                        data ->

                    _uiState.value=ApiState.Success(data)
                    Log.i(TAG, "getStoredWeatherModel:LOcalll/// ${ _uiState.value}")

                }

            Log.i(TAG, "getLocalProduct: ${_irepo.getStoredWeatherModel()}")
        }
    }

}

