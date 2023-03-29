package com.example.weatherforecastapplication.favourite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.productmvvm.model.RepositoryInterface

class FavouriteViewModelFactory (private val _irepo: RepositoryInterface): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FavouriteViewModel::class.java)){
            FavouriteViewModel(_irepo) as T
        }else{
            throw IllegalArgumentException("ViewModel class not found")
        }
    }
}