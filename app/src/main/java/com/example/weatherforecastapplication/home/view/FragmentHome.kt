package com.example.weatherforecastapplication.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.productmvvm.model.Repository
import com.example.productmvvm.network.WeatherClient
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.WeatherViewModel
import com.example.weatherforecastapplication.WeatherViewModelFactory
import com.example.weatherforecastapplication.databinding.FragmentHomeBinding
import com.example.weatherforecastapplication.model.Daily

import com.example.weatherforecastapplication.model.Hourly
import com.example.weatherforecastapplication.network.ApiState
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class FragmentHome : Fragment() {

    lateinit var binding: FragmentHomeBinding

    lateinit var allFactory: WeatherViewModelFactory
    lateinit var viewModel: WeatherViewModel

    lateinit var recyclerAdapterHourHome: AdapterHourlyHome
    var hourList= mutableListOf<Hourly>()

    lateinit var recyclerAdapterDaysHome: AdapterDaysHome
    var daysList= mutableListOf<Daily>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       // return inflater.inflate(R.layout.fragment_home, container, false)
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //curent toDay


        //24 per day
        recyclerAdapterHourHome= AdapterHourlyHome(hourList)
        //initialization
        binding.recyclerViewHourHome.adapter=recyclerAdapterHourHome
//        recyclerAdapterHourHome.setData(getHourly())
//        recyclerAdapterHourHome.notifyDataSetChanged()


        //days
        recyclerAdapterDaysHome= AdapterDaysHome(daysList)
        //initialization
        binding.recyclerViewDaysHome.adapter=recyclerAdapterDaysHome
//        recyclerAdapterDaysHome.setData(daysList)
//        recyclerAdapterDaysHome.notifyDataSetChanged()



        allFactory= WeatherViewModelFactory(

            Repository.getInstance(
                WeatherClient.getInstance()
            ))

        viewModel= ViewModelProvider(this,allFactory).get(WeatherViewModel::class.java)

        /////////*****////



        lifecycleScope.launch {

            viewModel.uiState.collectLatest { uiState ->when (uiState) {
                is ApiState.Success-> {
                    binding.progressBarHome?.visibility  = View.GONE
                    binding.recyclerViewHourHome.visibility = View.VISIBLE
                    binding.recyclerViewDaysHome.visibility = View.VISIBLE


                    println("//////**** Current ****//////")

                    var weatherList=uiState.data.current.weather
                    for(i in 0..weatherList.size-1){
                        var weatherDescription=weatherList.get(i).description
                        var weatherIcon=weatherList.get(i).icon
                        var weatherMain=weatherList.get(i).main
                        println("weatherDescription: $weatherDescription \nweatherIcon: $weatherIcon \nweatherMain: $weatherMain ")
                        binding.textViewDescriptionTodayHome.text="$weatherDescription"
                        changeIconWeather(weatherIcon)

                    }
                    // println("/////weather ${uiState.data.current.weather.toString()} Size: ${weatherList.size}")
                    println("/////clouds ${uiState.data.current.clouds.toString()} %")
                    binding.textViewCloud.text="${uiState.data.current.clouds.toString()} %"

                    println("/////current temp ${uiState.data.current.temp.toString()} C")
                    binding.textViewTempTodayHome.text="${uiState.data.current.temp.toString()} C"

                    println("/////humidity ${uiState.data.current.humidity} %")
                    binding.textViewHumidity.text=uiState.data.current.humidity.toString()+"%"

                    println("/////pressure ${uiState.data.current.pressure} hpa")
                    binding.textViewPressure.text="${uiState.data.current.pressure} hpa"

                    println("/////wind speed ${uiState.data.current.wind_speed.toString()}//n meter/sec")
                    binding.textViewWindSpeed.text="${uiState.data.current.wind_speed.toString()}meter/sec"

                    println("/////alerts ${uiState.data.alerts.toString()} meter/sec")

                    val currentDate=uiState.data.current.dt
                    // yyyy-MM-dd
                    val dateTime = Date(currentDate*1000)
                    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dateTime)
                    print("////Current Date:  ${formattedDate}  ")
                    val formateTime=SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(currentDate*1000))
                    println("/////Current Time-12-hour clock:  ${ SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                        Date(currentDate*1000)
                    )}")

                    binding.textViewCurrentDateTimeHome.text="${formattedDate}   $formateTime "

                    val sunrise=uiState.data.current.sunrise
                    val sunriseDate = Date(sunrise*1000)
                    print("/////sunrise Day/////////// ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(sunriseDate)} ")
                    println("/////sunrise Time-12-hour clock:  ${ SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
                        Date(sunrise*1000)
                    )}")
                    val sunset= Date(uiState.data.current.sunset * 1000)
                    print("/////sunset Day//////////////// ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(sunset)} ")
                    println("/////sunset Time:  ${ SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(sunset)}")


                    binding.textViewUVI.text="${uiState.data.current.uvi.toString()}"


                    println("//////**** Dayliy ****//////")

                    var dailyList=uiState.data.daily
                    for(i in 0..dailyList.size-1){
                        var daily=dailyList.get(i)
                        var dailyDay=dailyList.get(i).dt
                        var currentDailyDay= Date(dailyDay * 1000)
                        var dateDay= SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(currentDailyDay)
                        var timeDay= SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(dailyDay * 1000))
                        val df: DateFormat = SimpleDateFormat("EEEE") //yyyy-MM-E //uuuu-MM-EEE //EEEEEEE
                        println("day $i Date: $dateDay , Time: $timeDay , day name: ${df.format(currentDailyDay)}")
                        println("Temp Min: ${daily.temp.min} , Temp Max: ${daily.temp.max} , Weather: ${daily.weather.toString()}")
                    }
                    recyclerAdapterDaysHome.setData(dailyList)
                    recyclerAdapterDaysHome.notifyDataSetChanged()


                    println("//////**** hourly ****//////")

                    var hourlyList=uiState.data.hourly
                    for(i in 0..24){
                        var hourly=hourlyList.get(i)
                        var hourlyTime=hourlyList.get(i).dt
                        var currenthourlyDay= Date(hourlyTime * 1000)
                        var dateDay= SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(currenthourlyDay)
                        var timeDay= SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(hourlyTime * 1000))
                        var time24Hour= SimpleDateFormat("HH:mm", Locale.ENGLISH).format(Date(hourlyTime * 1000))
                        println("[$i] Day H: $dateDay, Time H-12-hour clock : $timeDay Time H-24-hour clock: $time24Hour ,Temp H: ${hourly.temp}")

                    }
                    recyclerAdapterHourHome.setData(hourlyList)
                    recyclerAdapterHourHome.notifyDataSetChanged()




//                    recyclerAdapter.setData(uiState.data)
//                    recyclerAdapter.notifyDataSetChanged()

                }
                is ApiState.Loading->{
                    println("initial")
                    binding.progressBarHome?.visibility= View.VISIBLE
                    binding.recyclerViewHourHome.visibility = View.GONE
                    binding.recyclerViewDaysHome.visibility = View.GONE
                }

                else ->{
                    binding.progressBarHome?.visibility= View.GONE
                    println("Check your netwark")
                    Toast.makeText(requireContext(),"Check your netwark", Toast.LENGTH_SHORT).show()

                }

            }}


        }
    }

    fun changeIconWeather(iconapi:String) {
        when(iconapi){
            "01d","01n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.sun_clear_icon) //clear sky
            "02d","02n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.img_9)   //few clouds
            "03d","03n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.img_10)  //scattered clouds
            "04d","04n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.img_7)   //broken clouds
            "09d","09n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.img_14)  //shower rain
            "10d","10n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.img_13)  //rain
            "11d","11n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.img_6)   //thunderstorm
            "13d","13n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.img_2) //snow
            "50d","50n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.img_17) //mist
        }

    }


}