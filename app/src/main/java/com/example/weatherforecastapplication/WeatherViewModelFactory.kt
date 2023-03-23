package com.example.weatherforecastapplication

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.productmvvm.model.RepositoryInterface

class WeatherViewModelFactory (private val _irepo: RepositoryInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(WeatherViewModel::class.java)){
            WeatherViewModel(_irepo) as T
        }else{
            throw IllegalArgumentException("ViewModel class not found")
        }
    }
}
