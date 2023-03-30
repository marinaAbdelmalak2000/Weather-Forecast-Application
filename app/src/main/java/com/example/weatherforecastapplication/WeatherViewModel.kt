package com.example.weatherforecastapplication

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmvvm.model.RepositoryInterface
import com.example.weatherforecastapplication.home.view.FragmentHome
import com.example.weatherforecastapplication.model.WeatherModel
import com.example.weatherforecastapplication.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.*


class WeatherViewModel (private val _irepo: RepositoryInterface): ViewModel() {

    private val _uiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val uiState = _uiState.asStateFlow()


    val language=_irepo.getPrameterSettingsLocal().getLanguage()
    val unit=_irepo.getPrameterSettingsLocal().getUnit()
    val longitude=_irepo.getPrameterSettingsLocal().longitude
    val latitude=_irepo.getPrameterSettingsLocal().latitude

    var checkTemp =_irepo.getPrameterSettingsLocal().convertCelToSpacificTemp()
    var checkSpeed=_irepo.getPrameterSettingsLocal().convertMeterPerSecToMilesPerHour()

    var indexLocationSetting=_irepo.getPrameterSettingsLocal().locationIndex

    fun allWeatherNetwork(
        latitude: String,
        longitude: String,
        exclude: String,
        units: String,
        language: String
    ){
        viewModelScope.launch (Dispatchers.IO){

            _irepo.getAllWeatherModel(latitude, longitude, exclude, units, language).catch {
                    e->_uiState.value=ApiState.Failure(e)
            }
                .collect{
                        data ->
                    Log.i(TAG, "allWeatherNetwork checkSpeed checkSpeedcheckSpeedcheckSpeed:== $checkSpeed  check temp: $checkTemp")
                    _uiState.value=ApiState.Success(data)
                    insertWeatherModel(data)
                    Log.i(TAG, "allWeatherNetwork: ${data.current?.wind_speed}")
                    Log.i(TAG, "insertWeatherModel(data): ${insertWeatherModel(data)}")
                }

            }
        }

    fun insertWeatherModel(weatherModel: WeatherModel){
        viewModelScope.launch (Dispatchers.IO){
            _irepo.insertWeatherModel(weatherModel)
        }
    }

    fun getLocalWeatherModel(){
        viewModelScope.launch (Dispatchers.IO){

            _irepo.getStoredWeatherModel()
                .catch {
                    e->_uiState.value=ApiState.Failure(e)
                Log.i(TAG, "getLocalWeatherModel: FailureFailureFailureFailure")
            }
                .collect{
                        data ->
                    _uiState.value=ApiState.Success(data)
                    Log.i(TAG, "getStoredWeatherModel:LOcalll/// ${ _uiState.value.toString()}  data ${data.current.temp}")
                }
        }
    }

    fun setLocal(activity: Activity, langCode:String){
        val local: Locale = Locale(langCode)
        Locale.setDefault(local)
        val resources : Resources =activity.resources
        val config: Configuration =resources.configuration
        config.setLocale(local)
        resources.updateConfiguration(config,resources.displayMetrics)
//        activity?.finish()
//        activity?.startActivity(activity?.intent)
    }

//    val newLocale = Locale("fr")
//    Locale.setDefault(newLocale)
//    val config = Configuration()
//    config.locale = newLocale
//    resources.updateConfiguration(config, resources.displayMetrics)
//
//    // Restart the activity to apply the new language
//    activity?.recreate()

}

