package com.example.weatherforecastapplication.settings.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.weatherforecastapplication.databinding.FragmentSettingsBinding


class FragmentSettings : Fragment() { //, AdapterView.OnItemSelectedListener

    lateinit var binding: FragmentSettingsBinding

    lateinit var lastSelectTempreture: SharedPreferences
    lateinit var editorTemp: SharedPreferences.Editor
    var selectTempreture:ArrayList<String> = arrayListOf("Celsius","Fahrenheit","Kelvin")

    lateinit var lastSelectWindSpeed: SharedPreferences
    lateinit var editorWindSpeed: SharedPreferences.Editor
    var selectWindSpeed:ArrayList<String> = arrayListOf("meter/sec","miles/hour")

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

        /////// Tempreture ////////////
        lastSelectTempreture= requireContext().getSharedPreferences("LastSettingTemp", Context.MODE_PRIVATE)
        editorTemp=lastSelectTempreture.edit()
        val lastClickTemp=lastSelectTempreture.getInt("LastClickTemp",0)
       // binding.spinnerTempretureSetting.onItemSelectedListener=this
        binding.spinnerTempretureSetting.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Get the selected item
                (view as TextView).setTextColor(Color.WHITE)
                (view as TextView).setTextSize(18f)
                editorTemp.putInt("LastClickTemp",position).commit()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
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
        lastSelectWindSpeed= requireContext().getSharedPreferences("LastSettingSpeed", Context.MODE_PRIVATE)
        editorWindSpeed=lastSelectWindSpeed.edit()
        val lastClickSpeed=lastSelectWindSpeed.getInt("LastClickSpeed",0)
      //  binding.spinnerWindSpeedeSetting.onItemSelectedListener=this
        binding.spinnerWindSpeedeSetting.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Get the selected item
                (view as TextView).setTextColor(Color.WHITE)
                (view as TextView).setTextSize(18f)
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


        //////////////////***********///////////////*******/////


        //store data
//        val preferences = requireActivity().getSharedPreferences("saveSettingPref", Context.MODE_PRIVATE)
//        val editor: SharedPreferences.Editor = preferences.edit()

//        editor.putString("language","$language")
//        editor.putString("units","imperial")
//        editor.putString("exclude","minutely")
//        editor.apply()

//        if (binding.radioButtonTempC.isChecked) {
//            editor.putString("language","C")
//            editor.apply()
//        } else if (binding.radioButtonTempK.isChecked) {
//            editor.putString("language","F")
//            editor.apply()
//        } else {
//            editor.putString("language","K")
//            editor.apply()
//        }

    }

}


