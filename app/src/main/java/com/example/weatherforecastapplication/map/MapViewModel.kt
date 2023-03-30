package com.example.weatherforecastapplication.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productmvvm.model.RepositoryInterface
import com.example.weatherforecastapplication.model.Favourite
import com.example.weatherforecastapplication.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MapViewModel (private val _irepo: RepositoryInterface): ViewModel() {

    private val _uiState = MutableStateFlow<ApiState>(ApiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
       // insertFavourite(Favourite())
    }

    fun insertFavourite(favouriteCity: Favourite){
        viewModelScope.launch (Dispatchers.IO){
            _irepo.insertFavourite(favouriteCity)
            //  println("insertFavourite///// $cityName //// $longMap ///// $latMap")
        }
    }
}