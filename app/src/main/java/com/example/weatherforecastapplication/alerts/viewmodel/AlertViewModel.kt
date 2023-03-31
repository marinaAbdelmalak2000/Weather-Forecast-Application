package com.example.weatherforecastapplication.alerts.viewmodel

import androidx.lifecycle.ViewModel
import com.example.productmvvm.model.RepositoryInterface
import com.example.weatherforecastapplication.model.CityAlarmList
import com.example.weatherforecastapplication.network.ApiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AlertViewModel (private val _irepo: RepositoryInterface): ViewModel() {

//    private val _uiState = MutableStateFlow<ApiState>(ApiState.Loading)
//    val uiState = _uiState.asStateFlow()

     fun getAlerts() {
     }

      fun insertAlert(alert: CityAlarmList) : Long{
        return 0
      }

     fun deleteAlert(id:Int){

     }

}