package com.example.weatherforecastapplication.alerts.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherforecastapplication.MainRule
import com.example.weatherforecastapplication.favourite.viewmodel.FavouriteViewModel
import com.example.weatherforecastapplication.model.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Matchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class AlertViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainRule = MainRule()

    @get:Rule
    var instance= InstantTaskExecutorRule()

    private lateinit var repository: RepositoryInterface
    lateinit var alertViewModel: AlertViewModel


    private var favoriteList: MutableList<Favourite> = mutableListOf<Favourite>(
        Favourite("City1","20.56","30.25"),
        Favourite("City2","25.56","33.25"),
        Favourite("City3","29.56","30.29"),
        Favourite("City3","30.56","30.25"),
    )

    private var cityAlarmList: MutableList<CityAlarmList> = mutableListOf<CityAlarmList>(
        CityAlarmList(1,16355126522,16355126522,5266552225,820023003),
        CityAlarmList(2,163548522,163548522,5266552225,820023003),
        CityAlarmList(3,16359856422,16359856422,5266552225,820023003),
        CityAlarmList(4,1985656522,1985656522,5266552225,820023003)
    )

    private var weatherModelNetwork: WeatherModel = WeatherModel(0,
        current =  Current(0,0.0,0,0.0,0,0,0,0,
            0.0,0.0,0, weather = emptyList(),0,0.0),
        lon = 52.2,
        lat = 45.0,
        timezone_offset = 55,
        timezone = "Africe/Cairo",
        hourly = emptyList(),
        daily = emptyList(), alerts = Alert("description",0,"event","stander",0,emptyList())
    )


    @Before
    fun setUp(){
        repository = FackRepository(favoriteList,cityAlarmList,weatherModelNetwork)
        alertViewModel= AlertViewModel(repository)

    }

    @Test
    fun getAlerts_returnNotNull() = mainRule.runBlockingTest{
        alertViewModel.getAlerts()
        val result=alertViewModel.uiState
        assertThat(result, CoreMatchers.`is`(notNullValue()))
    }


    @Test
    fun insertAlert_addAlertToList_returnNotNull()  = mainRule.runBlockingTest{
        // Given: add alert
        val alert =  CityAlarmList(5,16355126522,16355126522,5266552225,820023003)
        // When: insert alert
        alertViewModel.insertAlert(alert)
        // Then: alert list is not null
        assertThat(cityAlarmList.size, `is`(notNullValue()))
    }

    @Test
    fun deleteAlert_deletealertToList_returnSizeList4() = mainRule.runBlockingTest{
        // Given: delete alert
        val result = cityAlarmList[0]
        // When: delete alert
        alertViewModel.deleteAlert(result)
        // Then: alert list size =4
        assertThat(cityAlarmList.size,`is`(4))
    }
}