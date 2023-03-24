package com.example.productmvvm.db


import com.example.weatherforecastapplication.model.Setting

interface LocalSource {
//    suspend fun insertProduct(product: Product)
//    suspend fun deleteProduct(product: Product)
//    suspend fun getStoredProduct(): List<Product>

    fun getPrameterSettings(): Setting
}