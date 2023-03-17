package com.example.weatherforecastapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.productmvvm.model.RepositoryInterface

class AllWeatherViewModelFactory (private val _irepo: RepositoryInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(allWeatherViewModel::class.java)){
            allWeatherViewModel(_irepo) as T
        }else{
            throw IllegalArgumentException("ViewModel class not found")
        }
    }
}
