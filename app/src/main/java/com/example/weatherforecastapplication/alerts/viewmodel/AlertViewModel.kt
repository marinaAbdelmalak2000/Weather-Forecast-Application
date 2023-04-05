package com.example.weatherforecastapplication.alerts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapplication.alerts.view.AlertsWorkerManager
import com.example.weatherforecastapplication.model.CityAlarmList
import com.example.weatherforecastapplication.model.RepositoryInterface
import com.example.weatherforecastapplication.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlertViewModel (private val _irepo: RepositoryInterface): ViewModel() {

    private val _uiState = MutableStateFlow<ApiState<Any?>>(ApiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _uiStateInsert = MutableStateFlow<Long>(0)
    val uiStateInsert = _uiStateInsert.asStateFlow()

    private val _uiStateOneAlert = MutableStateFlow<ApiState<Any?>>(ApiState.Loading)
    val uiStateOneAlert = _uiStateOneAlert.asStateFlow()

     fun getAlerts() {
         viewModelScope.launch {
             _irepo.getAlerts().collect{ data->
                 _uiState.value=ApiState.SuccessAlert(data)
             }
         }
     }

    init {
        getAlerts()
    }

    fun insertAlert(alert: CityAlarmList){
        viewModelScope.launch {
            val id = _irepo.insertAlert(alert)
            _uiStateInsert.value = id

        }
    }

     fun deleteAlert(cityAlarmList: CityAlarmList){
         viewModelScope.launch (Dispatchers.IO){
             _irepo.deleteAlert(cityAlarmList.id)
         }
     }

//    fun getOneAlert(id: Int) {
//        viewModelScope.launch {
//            _irepo.getOneAlert(id).collect{ data->
//                _uiStateOneAlert.value=ApiState.SuccessOneAlert(data)
//            }
//        }
//    }

}