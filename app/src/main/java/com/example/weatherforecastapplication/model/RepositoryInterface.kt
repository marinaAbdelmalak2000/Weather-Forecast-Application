package com.example.productmvvm.model



import com.example.weatherforecastapplication.Current
import com.example.weatherforecastapplication.Daily
import com.example.weatherforecastapplication.WeatherModel
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun getAllProduct(): Daily
//    suspend fun getStoredProduct(): List<Product>
//    suspend fun insertProduct(product: Product)
//    suspend fun deleteProduct(product: Product)
}