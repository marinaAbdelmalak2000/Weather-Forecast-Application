package com.example.weatherforecastapplication.favourite.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecastapplication.model.RepositoryInterface
import com.example.weatherforecastapplication.model.Favourite
import com.example.weatherforecastapplication.model.WeatherModel
import com.example.weatherforecastapplication.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavouriteViewModel (private val _irepo: RepositoryInterface): ViewModel() {
    private val _Favourite = MutableStateFlow<ApiState>(ApiState.Loading)
    val favourite = _Favourite.asStateFlow()

    fun insertFavourite(favouriteCity: Favourite){
        viewModelScope.launch (Dispatchers.IO){
            _irepo.insertFavourite(favouriteCity)
             getLocalFavourite()
        }
    }
    init {
        getLocalFavourite()
    }

    fun getLocalFavourite(){
        viewModelScope.launch (Dispatchers.IO){

            _irepo.getStoredFavourite()

                .catch {
                        e->_Favourite.value=ApiState.Failure(e)
                    Log.i(ContentValues.TAG, "getLocalFavourite: FailureFailureFailureFailure")
                }
                .collect{
                        data ->
                    _Favourite.value=ApiState.SuccessFavourite(data)
                }

        }
    }

    fun deleteFavourite(favouriteCity: Favourite){
        viewModelScope.launch (Dispatchers.IO){
            _irepo.deleteFavourite(favouriteCity)
             getLocalFavourite()
        }
    }

}