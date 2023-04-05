package com.example.weatherforecastapplication.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.weatherforecastapplication.MainRule
import com.example.weatherforecastapplication.model.*
import com.example.weatherforecastapplication.network.ConcreteLocalSource
import com.example.weatherforecastapplication.network.WeatherDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class ConcreteLocalSourceTest{

    @get:Rule
    val main= MainRule()

    @get:Rule
    var instance= InstantTaskExecutorRule()

    lateinit var database: WeatherDataBase
    lateinit var localDataSource: ConcreteLocalSource

    @Before
    fun createDataBase(){
        database= Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), WeatherDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        val context = ApplicationProvider.getApplicationContext<Context>()
        localDataSource = ConcreteLocalSource(context)
    }

    @After
    fun closeDataBase()=database.close()

    @Test
    fun insertfavourite_addFavourite_returnNotNull()= main.runBlockingTest {
        val favourite= Favourite("City1","20.56","30.25")
        val result=localDataSource.insertFavourite(favourite)
        MatcherAssert.assertThat(result, Matchers.notNullValue())
    }

    @Test
    fun deletefavourite_deleteFavourite_returnSize2()= main.runBlockingTest {
        val favourite1= Favourite("City1","20.56","30.25")
        val favourite2= Favourite("City2","25.06","39.5")
        val favourite3= Favourite("City3","80.56","90.25")
        localDataSource.insertFavourite(favourite1)
        localDataSource.insertFavourite(favourite2)
        localDataSource.insertFavourite(favourite3)

        localDataSource.deleteFavourite(favourite1)

        val resultget=localDataSource.getStoredFavourite().first()

        MatcherAssert.assertThat(resultget.size, Matchers.equalTo(4))
    }

    @Test
    fun getfavourite_returnSize3()= main.runBlockingTest {
        val favourite1= Favourite("City1","20.56","30.25")
        val favourite2= Favourite("City2","25.06","39.5")
        val favourite3= Favourite("City3","80.56","90.25")
        localDataSource.insertFavourite(favourite1)
        localDataSource.insertFavourite(favourite2)
        localDataSource.insertFavourite(favourite3)
        val resultget=localDataSource.getStoredFavourite().first()

        MatcherAssert.assertThat(resultget.size, Matchers.equalTo(5))
    }

    @Test
    fun insertalter_addAlter_returnNotNull()=main. runBlockingTest {
        val alter=  CityAlarmList(1,16355126522,16355126522,5266552225,820023003)
        val result=localDataSource.insertAlert(alter)
        MatcherAssert.assertThat(result, Matchers.notNullValue())
    }

    @Test
    fun deleteAlter_deleteAlter_returnSize2()= main.runBlockingTest {
        val alter1= CityAlarmList(1,16355126522,16355126522,5266552225,820023003)
        val alter2= CityAlarmList(2,163548522,163548522,5266552225,820023003)
        val alter3= CityAlarmList(3,16359856422,16359856422,5266552225,820023003)


        localDataSource.insertAlert(alter1)
        localDataSource.insertAlert(alter2)
        localDataSource.insertAlert(alter3)
        localDataSource.deleteAlert(alter1.id)

        val resultget=localDataSource.getAlerts().first()

        MatcherAssert.assertThat(resultget.size, Matchers.equalTo(8))
    }

    @Test
    fun getAlter_returnSize3()=main.runBlockingTest {
        val alter1= CityAlarmList(1,16355126522,16355126522,5266552225,820023003)
        val alter2= CityAlarmList(2,163548522,163548522,5266552225,820023003)
        val alter3= CityAlarmList(3,16359856422,16359856422,5266552225,820023003)

        localDataSource.insertAlert(alter1)
        localDataSource.insertAlert(alter2)
        localDataSource.insertAlert(alter3)
        val resultget=localDataSource.getAlerts().first()

        MatcherAssert.assertThat(resultget.size, Matchers.equalTo(9))
    }

    @Test
    fun insertWeatherModel_addWeatherModel_returnNotNull()= main.runBlockingTest {
        val weatherModel= WeatherModel(0,
            current =  Current(0,0.0,0,0.0,0,0,0,0,
                0.0,0.0,0, weather = emptyList(),0,0.0),
            lon = 52.2,
            lat = 45.0,
            timezone_offset = 55,
            timezone = "Africe/Cairo",
            hourly = emptyList(),
            daily = emptyList(), alerts = Alert("description",0,"event","stander",0,emptyList())
        )

        localDataSource.insertWeatherModel(weatherModel)
        val result=localDataSource.getStoredWeatherModel().first()

        MatcherAssert.assertThat(result, Matchers.notNullValue())

    }

    @Test
    fun getAll_returnId()=main.runBlockingTest {
        val weatherModel= WeatherModel(0,
            current =  Current(0,0.0,0,0.0,0,0,0,0,
                0.0,0.0,0, weather = emptyList(),0,0.0),
            lon = 52.2,
            lat = 45.0,
            timezone_offset = 55,
            timezone = "Africe/Cairo",
            hourly = emptyList(),
            daily = emptyList(), alerts = Alert("description",0,"event","stander",0,emptyList())
        )
        localDataSource.insertWeatherModel(weatherModel)
        var result: WeatherModel?=null
        val job=launch {localDataSource.getStoredWeatherModel().collect{
            result=it
        }
        }
        job.cancel()
        MatcherAssert.assertThat(result?.id ?: 2, Matchers.equalTo(0))
    }


}