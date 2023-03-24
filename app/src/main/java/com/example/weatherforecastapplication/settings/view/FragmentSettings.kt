package com.example.weatherforecastapplication.settings.view

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.weatherforecastapplication.databinding.FragmentSettingsBinding


class FragmentSettings : Fragment(), AdapterView.OnItemSelectedListener {

    lateinit var binding: FragmentSettingsBinding

    lateinit var lastSelectTempreture: SharedPreferences
    lateinit var editorTemp: SharedPreferences.Editor

    var selectTempreture:ArrayList<String> = arrayListOf("C","F","K")

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

        lastSelectTempreture= requireContext().getSharedPreferences("LastSetting", Context.MODE_PRIVATE)
        editorTemp=lastSelectTempreture.edit()
        val lastClickTemp=lastSelectTempreture.getInt("LastClickTemp",0)


        binding.spinnerSetting.onItemSelectedListener=this


        val adapterTempreture = activity?.let {
            ArrayAdapter<String>(
                it,
                android.R.layout.simple_spinner_item,
                selectTempreture
            )
        }
        adapterTempreture?.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        binding.spinnerSetting.adapter=adapterTempreture

        binding.spinnerSetting.setSelection(lastClickTemp)


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


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        (view as TextView).setTextColor(Color.WHITE)
        (view as TextView).setTextSize(18f)
        editorTemp.putInt("LastClickTemp",position).commit()
        Toast.makeText(requireContext(),"Select $position",Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}


