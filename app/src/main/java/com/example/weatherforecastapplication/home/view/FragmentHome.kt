package com.example.weatherforecastapplication.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.blue
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.databinding.FragmentHomeBinding

import com.example.weatherforecastapplication.model.Hourly
import com.example.weatherforecastapplication.model.getHourly


class FragmentHome : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var recyclerAdapterHourHome: AdapterHourlyHome
    var hourList= mutableListOf<Hourly>()

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


        recyclerAdapterHourHome= AdapterHourlyHome(hourList)
        //initialization
        binding.recyclerViewHourHome.adapter=recyclerAdapterHourHome
        recyclerAdapterHourHome.setData(getHourly())
        recyclerAdapterHourHome.notifyDataSetChanged()
    }

}