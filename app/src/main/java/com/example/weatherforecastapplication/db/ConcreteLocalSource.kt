package com.example.productmvvm.db

import android.content.Context
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.Flow

class ConcreteLocalSource(context: Context) :LocalSource{

//    private val dao:ProductDao by lazy {
//        val db:ProductDataBase=ProductDataBase.getInstance(context)
//        db.getProductDao()
//    }
//    override suspend fun insertProduct(product: Product) {
//        dao?.insert(product)
//    }
//
//    override suspend fun deleteProduct(product: Product) {
//        dao?.delete(product)
//    }
//
//    override suspend fun getStoredProduct(): List<Product> {
//        return dao.getAll()
//    }
}