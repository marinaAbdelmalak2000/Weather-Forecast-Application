package com.example.weatherforecastapplication.settings.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.weatherforecastapplication.databinding.FragmentSettingsBinding
import java.util.Locale


class FragmentSettings : Fragment() { //, AdapterView.OnItemSelectedListener

     lateinit var binding: FragmentSettingsBinding

    lateinit var lastSelectSetting: SharedPreferences

    lateinit var editorTemp: SharedPreferences.Editor
    var selectTempreture:ArrayList<String> = arrayListOf("Celsius","Fahrenheit","Kelvin")


    lateinit var editorWindSpeed: SharedPreferences.Editor
    var selectWindSpeed:ArrayList<String> = arrayListOf("meter/sec","miles/hour")

    lateinit var editorLanguage: SharedPreferences.Editor
    var selectLanguage:ArrayList<String> = arrayListOf("Arabic","English")

    lateinit var editorLocation: SharedPreferences.Editor
    var selectLocation:ArrayList<String> = arrayListOf("GPS","Map")

    lateinit var editorNotification: SharedPreferences.Editor



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

        ///*********//////////**********/////////////**

        lastSelectSetting= requireContext().getSharedPreferences("LastSetting", Context.MODE_PRIVATE)

        /////// Tempreture ////////////

        editorTemp=lastSelectSetting.edit()
        val lastClickTemp=lastSelectSetting.getInt("LastClickTemp",0)
       // binding.spinnerTempretureSetting.onItemSelectedListener=this
        binding.spinnerTempretureSetting.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                    editorTemp.putInt("LastClickTemp",position).commit()

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        val adapterTempreture = activity?.let {
            ArrayAdapter<String>(
                it,
                android.R.layout.simple_spinner_item,
                selectTempreture
            )
        }
        adapterTempreture?.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        binding.spinnerTempretureSetting.adapter=adapterTempreture

        binding.spinnerTempretureSetting.setSelection(lastClickTemp)

        /////// Wind Speed ////////////
        editorWindSpeed=lastSelectSetting.edit()
        val lastClickSpeed=lastSelectSetting.getInt("LastClickSpeed",0)
      //  binding.spinnerWindSpeedeSetting.onItemSelectedListener=this
        binding.spinnerWindSpeedeSetting.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Get the selected item
//                (view as TextView).setTextColor(Color.WHITE)
//                (view as TextView).setTextSize(18f)
                editorWindSpeed.putInt("LastClickSpeed",position).commit()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        val adapterWindSpeed = activity?.let {
            ArrayAdapter<String>(
                it,
                android.R.layout.simple_spinner_item,
                selectWindSpeed
            )
        }
        adapterWindSpeed?.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        binding.spinnerWindSpeedeSetting.adapter=adapterWindSpeed
        binding.spinnerWindSpeedeSetting.setSelection(lastClickSpeed)


        /////// Language ////////////
        editorLanguage=lastSelectSetting.edit()
        val lastClickLanguage=lastSelectSetting.getInt("LastClickLanguage",0)
        //  binding.spinnerWindSpeedeSetting.onItemSelectedListener=this
        binding.spinnerLaunguageSetting.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Get the selected item
//                (view as TextView).setTextColor(Color.WHITE)
//                (view as TextView).setTextSize(18f)
                editorLanguage.putInt("LastClickLanguage",position).commit()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        val adapterLanguage = activity?.let {
            ArrayAdapter<String>(
                it,
                android.R.layout.simple_spinner_item,
                selectLanguage
            )
        }
        adapterLanguage?.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        binding.spinnerLaunguageSetting.adapter=adapterLanguage
        binding.spinnerLaunguageSetting.setSelection(lastClickLanguage)


        /////// Location ////////////

        editorLocation=lastSelectSetting.edit()
        val lastClickLocation=lastSelectSetting.getInt("LastClickLocation",0)
        binding.spinnerLocationSetting.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Get the selected item
//                (view as TextView).setTextColor(Color.WHITE)
//                (view as TextView).setTextSize(18f)
                editorLocation.putInt("LastClickLocation",position).commit()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        val adapterLocation = activity?.let {
            ArrayAdapter<String>(
                it,
                android.R.layout.simple_spinner_item,
                selectLocation
            )
        }
        adapterLocation?.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        binding.spinnerLocationSetting.adapter=adapterLocation

        binding.spinnerLocationSetting.setSelection(lastClickLocation)

        /////// Notification ////////////
        editorNotification=lastSelectSetting.edit()
        val lastClickNotification = lastSelectSetting.getBoolean("LastClickNotification", false)

        binding.switchNotification.setOnClickListener {
            editorNotification.putBoolean("LastClickNotification", binding.switchNotification.isChecked)
            editorNotification.commit()
        }

        binding.switchNotification.isChecked=lastClickNotification

        //////////////////***********///////////////*******/////



    }



}


