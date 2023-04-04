package com.example.weatherforecastapplication.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weatherforecastapplication.MainRule
import com.example.weatherforecastapplication.model.*
import com.example.weatherforecastapplication.network.WeatherDataBase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DaoTest {


    @get:Rule
    val main= MainRule()

    @get:Rule
    var instance= InstantTaskExecutorRule()

    lateinit var database: WeatherDataBase

    @Before
    fun createDataBase(){
        database= Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), WeatherDataBase::class.java).build()
    }

    @After
    fun closeDataBase()=database.close()


    @Test
    fun insertfavourite_addFavourite_returnNotNull()= main.runBlockingTest {
        val favourite= Favourite("City1","20.56","30.25")
        val result=database.getWeatherDao().insertfavourite(favourite)
        MatcherAssert.assertThat(result, Matchers.notNullValue())
    }

    @Test
    fun deletefavourite_deleteFavourite_returnSize2()= main.runBlockingTest {
        val favourite1= Favourite("City1","20.56","30.25")
        val favourite2= Favourite("City2","25.06","39.5")
        val favourite3= Favourite("City3","80.56","90.25")
        database.getWeatherDao().insertfavourite(favourite1)
        database.getWeatherDao().insertfavourite(favourite2)
        database.getWeatherDao().insertfavourite(favourite3)
        database.getWeatherDao().deletefavourite(favourite3)

        val resultget=database.getWeatherDao().getAllFavourite().first()

        MatcherAssert.assertThat(resultget.size, Matchers.equalTo(2))
    }

    @Test
    fun getfavourite_returnSize3()= main.runBlockingTest {
        val favourite1= Favourite("City1","20.56","30.25")
        val favourite2= Favourite("City2","25.06","39.5")
        val favourite3= Favourite("City3","80.56","90.25")
        database.getWeatherDao().insertfavourite(favourite1)
        database.getWeatherDao().insertfavourite(favourite2)
        database.getWeatherDao().insertfavourite(favourite3)
        val resultget=database.getWeatherDao().getAllFavourite().first()

        MatcherAssert.assertThat(resultget.size, Matchers.equalTo(3))
    }

    @Test
    fun insertalter_addAlter_returnNotNull()=main. runBlockingTest {
        val alter=  CityAlarmList(1,16355126522,16355126522,5266552225,820023003)
        val result=database.getWeatherDao().insertAlert(alter)
        MatcherAssert.assertThat(result, Matchers.notNullValue())
    }

    @Test
    fun deleteAlter_deleteAlter_returnSize2()= main.runBlockingTest {
        val alter1= CityAlarmList(1,16355126522,16355126522,5266552225,820023003)
        val alter2= CityAlarmList(2,163548522,163548522,5266552225,820023003)
        val alter3= CityAlarmList(3,16359856422,16359856422,5266552225,820023003)


        database.getWeatherDao().insertAlert(alter1)
        database.getWeatherDao().insertAlert(alter2)
        database.getWeatherDao().insertAlert(alter3)
        database.getWeatherDao().deleteAlert(1)

        val resultget=database.getWeatherDao().getAlerts().first()

        MatcherAssert.assertThat(resultget.size, Matchers.equalTo(2))
    }

    @Test
    fun getAlter_returnSize3()=main.runBlockingTest {
        val alter1= CityAlarmList(1,16355126522,16355126522,5266552225,820023003)
        val alter2= CityAlarmList(2,163548522,163548522,5266552225,820023003)
        val alter3= CityAlarmList(3,16359856422,16359856422,5266552225,820023003)

        database.getWeatherDao().insertAlert(alter1)
        database.getWeatherDao().insertAlert(alter2)
        database.getWeatherDao().insertAlert(alter3)
        val resultget=database.getWeatherDao().getAlerts().first()

        MatcherAssert.assertThat(resultget.size, Matchers.equalTo(3))
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

        database.getWeatherDao().insert(weatherModel)
        val result=database.getWeatherDao().getAlerts().first()

        MatcherAssert.assertThat(result.size, Matchers.notNullValue())

    }

    @Test
    fun getAll_returnId()=main.runBlockingTest {
        val weatherModel= WeatherModel(1,
            current =  Current(0,0.0,0,0.0,0,0,0,0,
                0.0,0.0,0, weather = emptyList(),0,0.0),
            lon = 52.2,
            lat = 45.0,
            timezone_offset = 55,
            timezone = "Africe/Cairo",
            hourly = emptyList(),
            daily = emptyList(), alerts = Alert("description",0,"event","stander",0,emptyList())
        )
        database.getWeatherDao().insert(weatherModel)
        var result:WeatherModel?=null
        val job=launch { database.getWeatherDao().getAll().collect{
            result=it
        }
        }
        job.cancel()
        MatcherAssert.assertThat(result?.id ?: 2, Matchers.equalTo(1))
    }

}