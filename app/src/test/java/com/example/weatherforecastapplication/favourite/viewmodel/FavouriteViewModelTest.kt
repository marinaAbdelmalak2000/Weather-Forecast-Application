package com.example.weatherforecastapplication.favourite.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherforecastapplication.MainRule
import com.example.weatherforecastapplication.db.ConcreteLocalSourceTest
import com.example.weatherforecastapplication.model.*
import com.example.weatherforecastapplication.network.RemoteSourceTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
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
class FavouriteViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
     var mainRule = MainRule()

    @get:Rule
    var instance= InstantTaskExecutorRule()

    private lateinit var repository: RepositoryInterface
    lateinit var favouriteViewModel: FavouriteViewModel


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
        favouriteViewModel=FavouriteViewModel(repository)

    }


    @Test
    fun insertFavourite_addFavouriteToList_returnNotNull()  = mainRule.runBlockingTest{
        // Given: add favorite
        val favourite = Favourite("City3","30.56","30.25")
        // When: insert favorite
        favouriteViewModel.insertFavourite(favourite)
        // Then: favourite list is not null
        assertThat(favoriteList.size,`is`(Matchers.notNullValue()))
    }

    @Test
    fun getLocalFavourite_returnNotNull() = mainRule.runBlockingTest{
        favouriteViewModel.getLocalFavourite()
        val favouriteData=favouriteViewModel.favourite
        assertThat(favouriteData,`is`(notNullValue()))
    }


    @Test
    fun deleteFavourite_deleteFavouriteToList_returnSizeList4() = mainRule.runBlockingTest{
        // Given: delete favorite
        val favourite = favoriteList[0]
        // When: delete favorite
        favouriteViewModel.deleteFavourite(favourite)
        // Then: favourite list size =4
        assertThat(favoriteList.size,`is`(4))
    }
}