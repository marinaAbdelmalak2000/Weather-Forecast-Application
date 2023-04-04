package com.example.weatherforecastapplication.alerts.view


import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.PixelFormat
import android.icu.text.ListFormatter.Width
import android.media.MediaPlayer
import android.os.Build
import android.provider.Settings

import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi

import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.weatherforecastapplication.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//lateinit var window: Window
var mediaPlayer : MediaPlayer?=null
class OneTimeWorkManager (private val context: Context, workerParams: WorkerParameters) :
    CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val description = inputData.getString("description")!!
        Log.i(TAG, "do00000000000000000000Work whyyyyyyyy!!!!!!!:  $description")

        val sharedLocation: SharedPreferences = context.getSharedPreferences("AlertSitting", Context.MODE_PRIVATE)
        val checkAlertType=sharedLocation.getBoolean("isClick",false)
        Log.i(TAG, "checkAlertType: ${checkAlertType} ")
        if(checkAlertType==false){
            Notification(context).createNotification("Weather state:)",description.toString())
        }
        else{
            Log.i(TAG, "Alermmmmmmmmmmmmmmmmm: ")
            fireAlarmDialog(description)
        }

        return Result.success()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private suspend fun fireAlarmDialog(description:String) {
        val alarmLayout=LayoutInflater.from(context).inflate(R.layout.dialog_alert,null)
        val messageHolder=alarmLayout.findViewById<TextView>(R.id.textViewDescAlert)
        messageHolder.text=description
        val btnClose=alarmLayout.findViewById<TextView>(R.id.textViewYes)

        val windowManager=context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val layoutParams=WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT
        )
        layoutParams.gravity= Gravity.TOP xor Gravity.CENTER
        layoutParams.horizontalWeight = layoutParams.horizontalWeight
        withContext(Dispatchers.Main){
            windowManager.addView(alarmLayout,layoutParams)

        }
        btnClose.setOnClickListener{
            mediaPlayer?.stop()
            alarmLayout.visibility=View.GONE
            windowManager.removeView(alarmLayout)
        }
        alertSound()
    }
    fun alertSound(){
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.alarm)
            mediaPlayer!!.isLooping = true
            mediaPlayer!!.start()
        }
    }



}