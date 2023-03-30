package com.example.weatherforecastapplication.model

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.util.Log


data class Setting (val languageIndex: Int,val speedIndex:Int,val tempretureIndex:Int,val locationIndex:Int,val longitude:String?,val latitude:String?){


    //LastLocation

    fun getLocation():String{
        if(locationIndex==0){
            return "GPS"
        }
        else{
           return "Map"
        }
    }


    fun getLanguage():String{
        if (languageIndex==1){
            return "en"
        }
        else{
            return "ar"
        }
    }

    //unit ==>(metric,standard,imperial)

    //metric==> tempreture:c--> index 0 and speed:meter/sec--> index 0
    //standard==> tempreture:k--> index 2 and speed:meter/sec--> index 0
    //imperial==>tempreture:f--> index 1 and speed:miles/hour--> index 1
    fun getUnit():String{
        return "metric"
    }
    fun convertCelToSpacificTemp():String{
        if(tempretureIndex==1){
           // convert celsius to fahrenheit
            return "F"
        }
        else if(tempretureIndex==2){
            // convert celsius to Kelvien
            return "K"
        }else{
            //celsius
            return "C"
        }

    }
    fun convertMeterPerSecToMilesPerHour():String{
        if(speedIndex==1){
            // meter/sec to miles/hour
            return "miles/hour"
        }
        else{
            //meter/sec
            return "meter/sec"
        }

    }
}