package com.example.weatherforecastapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmvvm.model.RepositoryInterface

import com.example.weatherforecastapplication.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class allWeatherViewModel (private val _irepo: RepositoryInterface): ViewModel(){

//    private val _weather : MutableLiveData<WeatherModel?> = MutableLiveData<WeatherModel?>()
//    val weather : MutableLiveData<WeatherModel?> = _weather

    private val _uiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        allWeatherNetwork()
    }


//    fun allWeatherNetwork(){
//        viewModelScope.launch (Dispatchers.IO){
//            _weather.postValue(_irepo.getAllProduct())
//            println("********************************************" +
//                    "//////////////" +
//                    "whhhhhhhhhhyyyyy ${_irepo.getAllProduct().weather}")
//
//        }
//
//    }

    fun allWeatherNetwork(){
        viewModelScope.launch (Dispatchers.IO){

            _irepo.getAllProduct().catch {
                    e->_uiState.value=ApiState.Failure(e)
            }
                .collect{
                        data -> _uiState.value=ApiState.Success(data)
                }
        }

    }


}