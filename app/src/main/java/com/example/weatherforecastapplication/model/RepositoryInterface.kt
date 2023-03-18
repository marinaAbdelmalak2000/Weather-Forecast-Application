package com.example.productmvvm.model

import com.example.weatherforecastapplication.model.WeatherModel
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun getAllProduct(): Flow<WeatherModel>
//    suspend fun getStoredProduct(): List<Product>
//    suspend fun insertProduct(product: Product)
//    suspend fun deleteProduct(product: Product)
}