package com.example.weatherforecastapplication.favourite.view

import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs

import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.WeatherViewModel
import com.example.weatherforecastapplication.WeatherViewModelFactory
import com.example.weatherforecastapplication.databinding.FragmentFavouriteBinding
import com.example.weatherforecastapplication.model.Daily
import com.example.weatherforecastapplication.model.Hourly
import com.example.weatherforecastapplication.model.Repository
import com.example.weatherforecastapplication.network.ApiState
import com.example.weatherforecastapplication.network.ConcreteLocalSource
import com.example.weatherforecastapplication.network.WeatherClient
import com.example.weatherforecastapplication.utils.NetwarkInternet
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class FragmentFavourite : Fragment() {

    lateinit var allFactory: WeatherViewModelFactory
    lateinit var viewModel: WeatherViewModel
    lateinit var binding: FragmentFavouriteBinding

    lateinit var recyclerAdapterHourHome: AdapterHourlyFavourite
    var hourList= mutableListOf<Hourly>()

    lateinit var recyclerAdapterDaysHome: AdapterDaysFavourite
    var daysList= mutableListOf<Daily>()

    val netwarkInternet= NetwarkInternet()
    lateinit var cityName:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentFavouriteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favNavigationArgs : FragmentFavouriteArgs by navArgs()

        var latFavourite=favNavigationArgs.latitute
        var longFavourite=favNavigationArgs.longtute


        //24 per day
        // var temp=viewModel.checkTemp
        recyclerAdapterHourHome= AdapterHourlyFavourite(hourList)
        //initialization
        binding.recyclerViewHourFavourite.adapter=recyclerAdapterHourHome

        //days
        recyclerAdapterDaysHome= AdapterDaysFavourite(daysList)
        //initialization
        binding.recyclerViewDaysFavourite.adapter=recyclerAdapterDaysHome

        allFactory= WeatherViewModelFactory(

            Repository.getInstance(
                WeatherClient.getInstance(), ConcreteLocalSource(requireContext())
            ))

        viewModel= ViewModelProvider(this,allFactory).get(WeatherViewModel::class.java)
        var language=viewModel.language
        val unit=viewModel.unit

        viewModel.allWeatherNetwork(latFavourite!!,longFavourite!!,"",unit,language)

        val geocoder: Geocoder

        geocoder = Geocoder(requireContext(), Locale.getDefault())
        val latFouble: Double? = latFavourite.toDouble()
        val longFouble: Double? = longFavourite.toDouble()
        val Adress = geocoder.getFromLocation(latFouble!!, longFouble!!,2)
        if(Adress!=null) {
            cityName = Adress.get(0).adminArea
        }
        else{
            Toast.makeText(requireContext(),"i can't found country",Toast.LENGTH_LONG)
                .show()
        }
        lifecycleScope.launch {

            viewModel.uiState.collectLatest { uiState ->when (uiState) {

                is ApiState.Success -> {
                    binding.progressBarFavourite.visibility=View.GONE
                    binding.recyclerViewHourFavourite.visibility = View.VISIBLE
                    binding.recyclerViewDaysFavourite.visibility = View.VISIBLE
                    binding.textViewCityNameFavourite.visibility = View.VISIBLE
                    binding.textViewCityNameFavourite.text=cityName

                    var weatherList = uiState.data.current.weather
                    for (i in 0..weatherList.size - 1) {
                        var weatherDescription = weatherList.get(i).description
                        var weatherIcon = weatherList.get(i).icon
                        binding.textViewDescriptionTodayFavourite.text = "$weatherDescription"
                        changeIconWeather(weatherIcon)

                    }
                    binding.textViewCloud.text =
                        "${uiState.data.current.clouds.toString()} %"

                    ////////////////////check temp convert celsius to fahrenheit and Kelvin /////////////////////////////

                    if (viewModel.checkTemp.equals("F")) {
                        //°F = (°C × 9/5) + 32
                        var convertDataTempF = (uiState.data.current.temp * (9 / 5)) + 32
                        val formattedDouble = String.format("%.2f", convertDataTempF)
                        binding.textViewTempTodayHome.text =
                            "${formattedDouble.toString()} " + "°F"
                    } else if (viewModel.checkTemp.equals("K")) {
                        //Kelvin = Celsius + 273.15
                        var convertDataTempK = uiState.data.current.temp + 273.15
                        val formattedDouble = String.format("%.2f", convertDataTempK)
                        binding.textViewTempTodayHome.text =
                            "${formattedDouble.toString()} " + "°K"
                    } else {
                        val formattedDouble =
                            String.format("%.2f", uiState.data.current.temp)
                        binding.textViewTempTodayHome.text =
                            "${formattedDouble.toString()}°C"
                    }

                    //////////////////////////////////////////////////////////////////////////////////////
                    binding.textViewHumidity.text =
                        uiState.data.current.humidity.toString() + "%"
                    binding.textViewPressure.text = "${uiState.data.current.pressure} hpa"


                    ////////////////////check speed miles/hour or meter/sec   /////////////////////////////

                    if (viewModel.checkSpeed.equals("miles/hour")) {
                        //convert meter/sec to miles/hour ==>  1 m/s = 2.23694 mph
                        var convertDataSpeed = uiState.data.current.wind_speed * 2.23694
                        val formattedDouble = String.format("%.2f", convertDataSpeed)
                        binding.textViewWindSpeed.text =
                            "${formattedDouble.toString()}miles/hour"
                    }
                    if (viewModel.checkSpeed.equals("meter/sec")) {
                        val formattedDouble =
                            String.format("%.2f", uiState.data.current.wind_speed)
                        binding.textViewWindSpeed.text =
                            "${formattedDouble.toString()}meter/sec"
                    }

                    val currentDate = uiState.data.current.dt
                    // yyyy-MM-dd
                    val dateTime = Date(currentDate * 1000)
                    val formattedDate =
                        SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dateTime)
                    //  print("////Current Date:  ${formattedDate}  ")
                    val formateTime = SimpleDateFormat(
                        "hh:mm a",
                        Locale.ENGLISH
                    ).format(Date(currentDate * 1000))
//                    println("/////Current Time-12-hour clock:  ${ SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
//                        Date(currentDate*1000)
//                    )}")

                    binding.textViewCurrentDateTimeFavourite.text =
                        "${formattedDate}   $formateTime "

                    val sunrise = uiState.data.current.sunrise
                    val sunriseDate = Date(sunrise * 1000)
                    val sunset = Date(uiState.data.current.sunset * 1000)
                    binding.textViewUVI.text = "${uiState.data.current.uvi.toString()}"

                    println("//////**** Dayliy ****//////")

                    var dailyList = uiState.data.daily
                    var temp = viewModel.checkTemp
                    recyclerAdapterDaysHome.setData(dailyList, temp)
                    recyclerAdapterDaysHome.notifyDataSetChanged()


                    println("//////**** hourly ****//////")

                    var hourlyList = uiState.data.hourly
                    //  var temp=viewModel.checkTemp
                    println("temp hourly send $temp")
                    recyclerAdapterHourHome.setData(hourlyList, temp)
                    recyclerAdapterHourHome.notifyDataSetChanged()

                }
                is ApiState.Loading->{
                    println("initial")
                    binding.progressBarFavourite?.visibility = View.VISIBLE
                    binding.recyclerViewHourFavourite.visibility = View.GONE
                    binding.recyclerViewDaysFavourite.visibility = View.GONE
                    binding.textViewCityNameFavourite.visibility = View.GONE
                }

                else ->{
                    // viewModel.refreshData()

                    //  println("Check your netwark ${viewModel.refreshData()}")
                    Toast.makeText(
                        requireContext(),
                        "Check your netwark",
                        Toast.LENGTH_SHORT
                    ).show()

                }


            }}
        }

    }

    fun changeIconWeather(iconapi:String) {
        when(iconapi){
            "01d","01n" -> binding.imageViewIconTodayFavourite.setBackgroundResource(R.drawable.sun_clear_icon) //clear sky
            "02d","02n" -> binding.imageViewIconTodayFavourite.setBackgroundResource(R.drawable.img_9)   //few clouds
            "03d","03n" -> binding.imageViewIconTodayFavourite.setBackgroundResource(R.drawable.img_10)  //scattered clouds
            "04d","04n" -> binding.imageViewIconTodayFavourite.setBackgroundResource(R.drawable.img_7)   //broken clouds
            "09d","09n" -> binding.imageViewIconTodayFavourite.setBackgroundResource(R.drawable.img_14)  //shower rain
            "10d","10n" -> binding.imageViewIconTodayFavourite.setBackgroundResource(R.drawable.img_13)  //rain
            "11d","11n" -> binding.imageViewIconTodayFavourite.setBackgroundResource(R.drawable.img_6)   //thunderstorm
            "13d","13n" -> binding.imageViewIconTodayFavourite.setBackgroundResource(R.drawable.img_2) //snow
            "50d","50n" -> binding.imageViewIconTodayFavourite.setBackgroundResource(R.drawable.img_17) //mist
        }

    }
}