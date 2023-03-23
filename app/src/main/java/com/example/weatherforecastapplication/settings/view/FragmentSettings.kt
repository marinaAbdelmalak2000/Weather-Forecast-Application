package com.example.weatherforecastapplication.settings.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherforecastapplication.WeatherViewModel
import com.example.weatherforecastapplication.WeatherViewModelFactory
import com.example.weatherforecastapplication.databinding.FragmentSettingsBinding


class FragmentSettings : Fragment() {

    lateinit var binding: FragmentSettingsBinding

    lateinit var allFactory: WeatherViewModelFactory
    lateinit var viewModel: WeatherViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        allFactory= WeatherViewModelFactory(
//
//            Repository.getInstance(
//                WeatherClient.getInstance()
//            ))
//
//        viewModel= ViewModelProvider(this,allFactory).get(WeatherViewModel::class.java)
//
//        viewModel.allWeatherNetwork(33.44,-94.04,"minutely","imperial ","ar")

        val preferences = requireActivity().getSharedPreferences("saveSettingPref", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString("language","en")
        editor.putString("units","imperial")
        editor.putString("exclude","minutely")
        editor.apply()

    }

}