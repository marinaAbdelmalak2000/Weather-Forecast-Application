package com.example.weatherforecastapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapplication.home.view.FragmentHome
import com.example.weatherforecastapplication.model.CurrentLocation
import com.example.weatherforecastapplication.model.RepositoryInterface
import com.example.weatherforecastapplication.model.WeatherModel
import com.example.weatherforecastapplication.network.ApiState
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.*


class WeatherViewModel (private val _irepo: RepositoryInterface): ViewModel() {

    private val _uiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val uiState = _uiState.asStateFlow()


    private val _Location: MutableLiveData<CurrentLocation> = MutableLiveData<CurrentLocation>()
    val Location :LiveData<CurrentLocation>
    get() = _Location



    val language=_irepo.getPrameterSettingsLocal().getLanguage()
    val unit=_irepo.getPrameterSettingsLocal().getUnit()
    val longitude=_irepo.getPrameterSettingsLocal().longitude
    val latitude=_irepo.getPrameterSettingsLocal().latitude

    var checkTemp =_irepo.getPrameterSettingsLocal().convertCelToSpacificTemp()
    var checkSpeed=_irepo.getPrameterSettingsLocal().convertMeterPerSecToMilesPerHour()

    var indexLocationSetting=_irepo.getPrameterSettingsLocal().locationIndex

  //  var getLocation=_irepo.getPrameterSettingsLocal().getLocation()


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
                //    Log.i(TAG, "allWeatherNetwork checkSpeed checkSpeedcheckSpeedcheckSpeed:== $checkSpeed  check temp: $checkTemp")
                    _uiState.value=ApiState.Success(data)
                    insertWeatherModel(data)
                   // Log.i(TAG, "allWeatherNetwork: ${data.current?.wind_speed}")
                   // Log.i(TAG, "insertWeatherModel(data): ${insertWeatherModel(data)}")
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
              //  Log.i(TAG, "getLocalWeatherModel: FailureFailureFailureFailure")
            }
                .collect{
                        data ->
                    _uiState.value=ApiState.Success(data)
                  //  Log.i(TAG, "getStoredWeatherModel:LOcalll/// ${ _uiState.value.toString()}  data ${data.current.temp}")
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

fun setLocation( location: CurrentLocation){
     _Location.postValue(location)
  //  Log.i(TAG, "setLocation: ${location.address}")

}

}

