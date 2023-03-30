package com.example.productmvvm.db

import androidx.room.*
import com.example.weatherforecastapplication.model.Favourite
import com.example.weatherforecastapplication.model.WeatherModel
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(weatherModel: WeatherModel)

    @Query("SELECT * FROM WeatherModel")
     fun getAll(): Flow<WeatherModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertfavourite(favouriteCity: Favourite)

    @Query("SELECT * FROM Favourite")
    fun getAllFavourite(): Flow<List<Favourite>>

    @Delete
    fun deletefavourite(favouriteCity: Favourite)

}