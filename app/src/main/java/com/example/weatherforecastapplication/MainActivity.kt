package com.example.weatherforecastapplication


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.productmvvm.model.Repository
import com.example.productmvvm.network.WeatherClient
import com.example.weatherforecastapplication.network.ApiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var allFactory: WeatherViewModelFactory
    lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /////////*****////

        allFactory= WeatherViewModelFactory(
            Repository.getInstance(
                WeatherClient.getInstance()
            ))

        viewModel= ViewModelProvider(this,allFactory).get(WeatherViewModel::class.java)

        /////////*****////


        lifecycleScope.launch {

            viewModel.uiState.collectLatest { uiState ->when (uiState) {
                is ApiState.Success-> {
//                    binding.progressBar?.visibility  = View.GONE
//                    binding.recyclerView.visibility = View.VISIBLE



                    println("//////**** Current ****//////")

                    var weatherList=uiState.data.current.weather
                    for(i in 0..weatherList.size-1){
                        var weatherDescription=weatherList.get(i).description
                        var weatherIcon=weatherList.get(i).icon
                        var weatherMain=weatherList.get(i).main
                        println("weatherDescription: $weatherDescription \nweatherIcon: $weatherIcon \nweatherMain: $weatherMain ")
                    }
                   // println("/////weather ${uiState.data.current.weather.toString()} Size: ${weatherList.size}")
                    println("/////clouds ${uiState.data.current.clouds.toString()} %")
                    println("/////current temp ${uiState.data.current.temp.toString()} C")
                    println("/////humidity ${uiState.data.current.humidity} %")
                    println("/////pressure ${uiState.data.current.pressure} hpa")
                    println("/////wind speed ${uiState.data.current.wind_speed.toString()} meter/sec")

                    println("/////alerts ${uiState.data.alerts.toString()} meter/sec")

                    val currentDate=uiState.data.current.dt
                    // yyyy-MM-dd
                    val dateTime = Date(currentDate*1000)
                    val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dateTime)
                    print("////Current Date:  ${formattedDate}  ")
                    println("/////Current Time-12-hour clock:  ${ SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(currentDate*1000))}")

                    val sunrise=uiState.data.current.sunrise
                    val sunriseDate =Date(sunrise*1000)
                    print("/////sunrise Day/////////// ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(sunriseDate)} ")
                    println("/////sunrise Time-12-hour clock:  ${ SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sunrise*1000))}")
                    val sunset=Date(uiState.data.current.sunset * 1000)
                    print("/////sunset Day//////////////// ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(sunset)} ")
                    println("/////sunset Time:  ${ SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(sunset)}")


                    println("//////**** Dayliy ****//////")

                    var dailyList=uiState.data.daily
                    for(i in 0..dailyList.size-1){
                        var daily=dailyList.get(i)
                        var dailyDay=dailyList.get(i).dt
                        var currentDailyDay=Date(dailyDay * 1000)
                        var dateDay=SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(currentDailyDay)
                        var timeDay= SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(dailyDay * 1000))
                        val df: DateFormat = SimpleDateFormat("EEEE") //yyyy-MM-E //uuuu-MM-EEE //EEEEEEE
                        println("day $i Date: $dateDay , Time: $timeDay , day name: ${df.format(currentDailyDay)}")
                        println("Temp Min: ${daily.temp.min} , Temp Max: ${daily.temp.max} , Weather: ${daily.weather.toString()}")
                    }


                    println("//////**** hourly ****//////")

                    var hourlyList=uiState.data.hourly
                    for(i in 0..24){
                        var hourly=hourlyList.get(i)
                        var hourlyTime=hourlyList.get(i).dt
                        var currenthourlyDay=Date(hourlyTime * 1000)
                        var dateDay=SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(currenthourlyDay)
                        var timeDay= SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(hourlyTime * 1000))
                        var time24Hour=SimpleDateFormat("HH:mm", Locale.ENGLISH).format(Date(hourlyTime * 1000))
                        println("[$i] Day H: $dateDay, Time H-12-hour clock : $timeDay Time H-24-hour clock: $time24Hour ,Temp H: ${hourly.temp}")

                    }


//                    recyclerAdapter.setData(uiState.data)
//                    recyclerAdapter.notifyDataSetChanged()

                }
                is ApiState.Loading->{
                    println("initial")
//                    binding.progressBar?.visibility= View.VISIBLE
//                    binding.recyclerView.visibility= View.GONE
                }

                else ->{
//                    binding.progressBar?.visibility= View.GONE
                    println("Check your netwark")
                    Toast.makeText(applicationContext,"Check your netwark", Toast.LENGTH_SHORT).show()

                }

            }}

        }

//        //observation
//        viewModel.weather.observe(this){
//                weather->
//            Log.i(ContentValues.TAG, "onCreate: ${weather}")
//            if(weather!=null){
////                Log.i(TAG, "weatherrrrrrrrrrrr: ${weather}")
//                println("weatherrrrrrrrrrrr: ${weather.weather}")
//               // recyclerAdapter.setData(weather)
//               // recyclerAdapter.notifyDataSetChanged()
//               // binding.recyclerView.adapter=recyclerAdapter
//            }
//        }

    }
}