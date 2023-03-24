package com.example.productmvvm.db

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.asLiveData
import com.example.weatherforecastapplication.model.Setting
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
   private val sharedPreferences: SharedPreferences = context.getSharedPreferences("saveSettingPref", Context.MODE_PRIVATE)
    override fun getPrameterSettings(): Setting {
        val language = sharedPreferences.getString("language", "") ?: ""
        val units = sharedPreferences.getString("units", "") ?: ""
        val exclude = sharedPreferences.getString("exclude", "") ?: ""
        return Setting(language,units,exclude)
    }

}