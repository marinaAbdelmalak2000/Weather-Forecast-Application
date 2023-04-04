package com.example.weatherforecastapplication.settings.view

import android.app.Activity
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
import androidx.navigation.Navigation
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.databinding.FragmentSettingsBinding
import com.example.weatherforecastapplication.utils.NetwarkInternet
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

    val netwarkInternet= NetwarkInternet()


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
        if(netwarkInternet.isNetworkAvailable(context)) {
            editorTemp = lastSelectSetting.edit()
            val lastClickTemp = lastSelectSetting.getInt("LastClickTemp", 0)
            // binding.spinnerTempretureSetting.onItemSelectedListener=this
            binding.spinnerTempretureSetting.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {

                        editorTemp.putInt("LastClickTemp", position).commit()

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
            binding.spinnerTempretureSetting.adapter = adapterTempreture

            binding.spinnerTempretureSetting.setSelection(lastClickTemp)
        }else{
            Toast.makeText(activity,getString(R.string.not_netwark),Toast.LENGTH_SHORT).show()
        }
        /////// Wind Speed ////////////
        if(netwarkInternet.isNetworkAvailable(context)){
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
        }else{
            Toast.makeText(activity,"Please, open internet",Toast.LENGTH_SHORT).show()
        }

        /////// Language ////////////
        editorLanguage=lastSelectSetting.edit()
        val lastClickLanguage=lastSelectSetting.getInt("LastClickLanguage",0)
        binding.spinnerLaunguageSetting.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Get the selected item
//                (view as TextView).setTextColor(Color.WHITE)
//                (view as TextView).setTextSize(18f)
                editorLanguage.putInt("LastClickLanguage",position).commit()
                if(position==0){
                    setLocal(requireActivity(),"ar")
                   // changeLanguage("ar")
                }
                else{
                    setLocal(requireActivity(),"en")
                   // changeLanguage("en")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
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
        if(netwarkInternet.isNetworkAvailable(context)) {
            editorLocation = lastSelectSetting.edit()
            val lastClickLocation = lastSelectSetting.getInt("LastClickLocation", 0)
            binding.spinnerLocationSetting.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        // Get the selected item
        //                (view as TextView).setTextColor(Color.WHITE)
        //                (view as TextView).setTextSize(18f)
                        editorLocation.putInt("LastClickLocation", position).commit()
                        if(position==1){
                            Navigation.findNavController(requireView()).navigate(R.id.action_fragmentSettings_to_fragmentMapLocationHome)

                        }
//                        else{
//                            setLocal(requireActivity(),"en")
//                            // changeLanguage("en")
//                        }
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
            binding.spinnerLocationSetting.adapter = adapterLocation

            binding.spinnerLocationSetting.setSelection(lastClickLocation)
         }else{
                Toast.makeText(activity,getString(R.string.not_netwark),Toast.LENGTH_SHORT).show()
         }
        /////// Notification ////////////
        if(netwarkInternet.isNetworkAvailable(context)){
            editorNotification=lastSelectSetting.edit()
            val lastClickNotification = lastSelectSetting.getBoolean("LastClickNotification", false)

            binding.switchNotification.setOnClickListener {
                editorNotification.putBoolean("LastClickNotification", binding.switchNotification.isChecked)
                editorNotification.commit()
            }

            binding.switchNotification.isChecked=lastClickNotification
        }else{
            Toast.makeText(activity,getString(R.string.not_netwark),Toast.LENGTH_SHORT).show()
        }
        //////////////////***********///////////////*******/////



    }

    fun setLocal(activity: Activity, langCode:String){
        val local: Locale = Locale(langCode)
        Locale.setDefault(local)
        val resources : Resources =activity.resources
        val config: Configuration =resources.configuration
        config.setLocale(local)
        resources.updateConfiguration(config,resources.displayMetrics)

       // (context as Activity).recreate()
    }


//    fun setLocale(context: Context, languageCode: String) {
//        val locale = Locale(languageCode)
//        Locale.setDefault(locale)
//        val resources = context.resources
//        val configuration = Configuration(resources.configuration)
//        configuration.setLocale(locale)
//        context.createConfigurationContext(configuration)
//        context.resources.updateConfiguration(configuration, resources.displayMetrics)
//        // Recreate the activity to apply the new locale configuration
//        (context as Activity).recreate()
//    }


}


