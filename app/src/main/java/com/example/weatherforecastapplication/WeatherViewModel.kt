package com.example.weatherforecastapplication

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.withContext


class WeatherViewModel (private val _irepo: RepositoryInterface): ViewModel() {

    private val _uiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val uiState = _uiState.asStateFlow()

  //  private val _uiStateLocal = MutableStateFlow<ApiState>(ApiState.Loading)
  //  val uiStateLocal = _uiStateLocal.asStateFlow()


    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean>
        get() = _isConnected


//    lateinit var units :String
//    lateinit var exclude:String

    val language=_irepo.getPrameterSettingsLocal().getLanguage()
    val unit=_irepo.getPrameterSettingsLocal().getUnit()
    val longitude=_irepo.getPrameterSettingsLocal().longitude
    val latitude=_irepo.getPrameterSettingsLocal().latitude
//    var checkSpeed =_irepo.getPrameterSettingsLocal().CheckconvertSpeed()
//    var checkTemp=_irepo.getPrameterSettingsLocal().CheckconvertTemp()
    var checkTemp =_irepo.getPrameterSettingsLocal().convertCelToSpacificTemp()
    var checkSpeed=_irepo.getPrameterSettingsLocal().convertMeterPerSecToMilesPerHour()



    init {
        //getLocalWeatherModel()
      //  checkNetworkStatus()
      //  Log.i(TAG, "language::::::::::::::::::::::::::::::::: $language  Index::::: ${isConnected.value}")
      //  Log.i(TAG, " ////language : $language units $units exclude $exclude ")
      //  allWeatherNetwork(33.44,-94.04,"minutely", unit,language)

    }

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
                  //  withContext(Dispatchers.Main){
                    _uiState.value=ApiState.Success(data)
                    Log.i(TAG, "getStoredWeatherModel:LOcalll/// ${ _uiState.value.toString()}  data ${data.current.temp}")
                }


              //  }

            Log.i(TAG, "getLocalProduct: ${_irepo.getStoredWeatherModel().toString()} /// ${_uiState.value.toString()}")
        }
    }
    // Call this function to refresh the data
//    fun refreshData() {
//        viewModelScope.launch {
//            _irepo.fetchData()
//        }
//    }

//    private fun checkNetworkStatus() {
//        val connectivityManager = getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkInfo = connectivityManager.activeNetworkInfo
//        _isConnected.value = networkInfo != null && networkInfo.isConnected
//    }



}

