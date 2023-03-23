package com.example.productmvvm.model

import com.example.weatherforecastapplication.model.Setting
import com.example.weatherforecastapplication.model.WeatherModel
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun getAllWeatherModel( latitude:Double,longitude:Double,exclude:String,units:String,language:String,): Flow<WeatherModel>
    fun getUserSettings(): Setting
//    suspend fun getStoredProduct(): List<Product>
//    suspend fun insertProduct(product: Product)
//    suspend fun deleteProduct(product: Product)
}