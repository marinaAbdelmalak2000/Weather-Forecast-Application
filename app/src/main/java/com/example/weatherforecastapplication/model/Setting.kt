package com.example.weatherforecastapplication.model

import android.content.ContentValues.TAG
import android.util.Log

data class Setting (val languageIndex: Int,val speedIndex:Int,val tempretureIndex:Int){

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
   /*     if(speedIndex==0&&tempretureIndex==0){
            return "metric"
        }else if(speedIndex==0&&tempretureIndex==2){
            return "standard"
        }
        else if(speedIndex==1&&tempretureIndex==1){
            return "imperial"
        }
        else {//speedIndex==1&&tempretureIndex==0
            //convert speed miles/hour to meter/sec then convert meter/sec to miles/hour in home fragment
            return "metric"
        }*/
        return "metric"
    }
  /*  fun CheckconvertSpeed():Boolean{

        if(speedIndex==1&&tempretureIndex==0){
            // tempreture:c--> index 0 and speed:miles/hour--> index 1
            return true
        }else if(speedIndex==1&&tempretureIndex==2){
            // tempreture:F--> index 2 and speed:miles/hour--> index 1
            return true
        }
        else{
            return false
        }
    }

    fun CheckconvertTemp():Boolean{

        if(speedIndex==0&&tempretureIndex==1){
            // tempreture:f--> index 1 and speed:meter/sec--> index 0
            //convert celsius to fahrenheit
            //°F = (°C × 9/5) + 32
            return true
        }
        else{
            return false
        }
    }
    */
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