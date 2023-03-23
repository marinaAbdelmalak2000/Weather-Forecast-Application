package com.example.weatherforecastapplication

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmvvm.model.RepositoryInterface

import com.example.weatherforecastapplication.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class WeatherViewModel (private val _irepo: RepositoryInterface): ViewModel(){

    private val _uiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val uiState = _uiState.asStateFlow()

    val language=_irepo.getUserSettings().language
    val units=_irepo.getUserSettings().units
    val exclude=_irepo.getUserSettings().exclude

    init {
//        val language = sharedPref.getString("language", "ar")
        Log.i(TAG, " ////language : $language units $units exclude $exclude ")
//        if (language != null) {
            allWeatherNetwork(33.44,-94.04,exclude, units,language)
//        }
    }

    fun allWeatherNetwork(latitude:Double,longitude:Double,exclude:String,units:String,language:String,){
        viewModelScope.launch (Dispatchers.IO){

            _irepo.getAllWeatherModel(latitude,longitude,exclude,units,language,).catch {
                    e->_uiState.value=ApiState.Failure(e)
            }
                .collect{
                        data -> _uiState.value=ApiState.Success(data)
                }
        }

    }



}