package com.example.weatherforecastapplication.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.productmvvm.model.RepositoryInterface
import com.example.weatherforecastapplication.WeatherViewModel

class MapViewModelFactory (private val _irepo: RepositoryInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MapViewModel::class.java)){
            MapViewModel(_irepo) as T
        }else{
            throw IllegalArgumentException("ViewModel class not found")
        }
    }
}