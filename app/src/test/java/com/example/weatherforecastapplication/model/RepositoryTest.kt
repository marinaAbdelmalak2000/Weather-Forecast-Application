package com.example.weatherforecastapplication.model

import com.example.weatherforecastapplication.MainRule
import com.example.weatherforecastapplication.db.ConcreteLocalSourceTest
import com.example.weatherforecastapplication.network.RemoteSourceTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RepositoryTest {


    @ExperimentalCoroutinesApi
    @get:Rule
    var mainRule = MainRule()

    private lateinit var remoteSource:RemoteSourceTest
    private lateinit var localSource:ConcreteLocalSourceTest
    private lateinit var repository: Repository

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
        remoteSource = RemoteSourceTest( weatherModelNetwork)
        localSource = ConcreteLocalSourceTest(favoriteList,cityAlarmList )
        repository = Repository(remoteSource, localSource)

    }

    @Test
    fun getAllWeatherModel()=mainRule.runBlockingTest {
        val results = repository.getAllWeatherModel(
            latitude = "56.5",
            longitude = "54.5",
            exclude = "minutely",
            units ="metric" ,
            language = "en",
        ).first()

        // Then: response is a same of fake WeatherResponse
        assertThat(results,`is`(weatherModelNetwork))
    }

    @Test
    fun insertWeatherModel_addObject_resultIsNotNull() =mainRule.runBlockingTest{
        // Given: add object
       var result= WeatherModel(0, alerts = null,
            current = Current(0,0.0,0,0.0,0,0,0,0,
                0.0,0.0,0, weather = emptyList(),0,0.0),
            daily = emptyList() , hourly = emptyList(), lat =0.0, lon =0.0, timezone = "", timezone_offset = 0)
        // When: insert object
        repository.insertWeatherModel(result)
        // Then: object is not null
        assertThat(weatherModelNetwork,`is`(notNullValue()))
    }

    @Test
    fun getStoredFavourite_reternListOfSize() =mainRule.runBlockingTest {
        // When: get favourite
        val resutls = repository.getStoredFavourite()

        // Then: size of favorite = size favoriteList
        assertThat(resutls.first().size,`is`(favoriteList.size))
    }


    @Test
    fun insertFavorite_addFavorite_resultIsNotNull() = mainRule.runBlockingTest{
        // Given: add favorite
        val favourite = Favourite("City3","30.56","30.25")
        // When: insert favorite
        repository.insertFavourite(favourite)
        // Then: favourite list is not null
        assertThat(favoriteList.size,`is`(notNullValue()))
    }

    @Test
    fun deleteFavourite_checkDeleteByNumberList()=mainRule. runBlockingTest {
        // Given:  choose delete favorite
        val favourite = favoriteList[0]

        // When: delete favorite
        repository.deleteFavourite(favourite)

        // Then: size equal 3
        assertThat(favoriteList.size,`is`(3))
    }

    @Test
    fun getAlerts_reternListOfSize() =mainRule.runBlockingTest {
        // When: get alert
        val resutls = repository.getAlerts()

        // Then: size of alert = size cityAlarmList
        assertThat(resutls.first().size,`is`(cityAlarmList.size))
    }

    @Test
    fun insertalert_addTime_resultIsNotNull() = mainRule.runBlockingTest{
        // Given: add time
        val alert = CityAlarmList(5,1985656522,1985656522,5266552225,820023003)
        // When: insert alert
        repository.insertAlert(alert)
        // Then: alert list is not null
        assertThat(cityAlarmList.size,`is`(notNullValue()))
    }

    @Test
    fun deleteAlert_checkDeleteByNumberList()=mainRule.runBlockingTest {
        // Given:  choose delete favorite
        val alert = cityAlarmList[0]

        // When: delete favorite
        repository.deleteAlert(alert.id)

        // Then: size equal 3
        assertThat(cityAlarmList.size,`is`(3))
    }


}