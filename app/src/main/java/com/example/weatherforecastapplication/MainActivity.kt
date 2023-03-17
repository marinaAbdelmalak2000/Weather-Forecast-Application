package com.example.weatherforecastapplication


import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.productmvvm.model.Repository
import com.example.productmvvm.network.WeatherClient
import com.example.weatherforecastapplication.network.ApiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var allFactory: AllWeatherViewModelFactory
    lateinit var viewModel: allWeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /////////*****////

        allFactory= AllWeatherViewModelFactory(
            Repository.getInstance(
                WeatherClient.getInstance()
            ))

        viewModel= ViewModelProvider(this,allFactory).get(allWeatherViewModel::class.java)

        /////////*****////


//        lifecycleScope.launch {
//
//            viewModel.uiState.collectLatest { uiState ->when (uiState) {
//                is ApiState.Success-> {
////                    binding.progressBar?.visibility  = View.GONE
////                    binding.recyclerView.visibility = View.VISIBLE
//                    println("/////deeeee${uiState.data.weather}")
////                    recyclerAdapter.setData(uiState.data)
////                    recyclerAdapter.notifyDataSetChanged()
//
//                }
//                is ApiState.Loading->{
//                    println("initial")
////                    binding.progressBar?.visibility= View.VISIBLE
////                    binding.recyclerView.visibility= View.GONE
//                }
//
//                else ->{
////                    binding.progressBar?.visibility= View.GONE
//                    println("Check your netwark")
//                    Toast.makeText(applicationContext,"Check your netwark", Toast.LENGTH_SHORT).show()
//
//                }
//
//            }}
//
//        }

        //observation
        viewModel.weather.observe(this){
                weather->
            Log.i(ContentValues.TAG, "onCreate: ${weather}")
            if(weather!=null){
//                Log.i(TAG, "weatherrrrrrrrrrrr: ${weather}")
                println("weatherrrrrrrrrrrr: ${weather.weather}")
               // recyclerAdapter.setData(weather)
               // recyclerAdapter.notifyDataSetChanged()
               // binding.recyclerView.adapter=recyclerAdapter
            }
        }

    }
}