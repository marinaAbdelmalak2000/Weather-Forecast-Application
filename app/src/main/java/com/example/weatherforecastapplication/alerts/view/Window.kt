package com.example.weatherforecastapplication.alerts.view

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.databinding.DialogAlertBinding


class Window(private val context: Context,private val description:String) {

    private lateinit var  view: View
    private lateinit var windowManager: WindowManager
    private lateinit var binding: DialogAlertBinding

    fun createWindow() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        view = inflater.inflate(R.layout.dialog_alert, null)
        binding = DialogAlertBinding.bind(view)

        binding.textViewDescAlert.text = description
        binding.textViewYes.setOnClickListener {
            //close window
            close()
        }


        val layoutFlag: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }
        windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width=(context.resources.displayMetrics.widthPixels * 0.85).toInt()
        val params = WindowManager.LayoutParams(width,WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                    or WindowManager.LayoutParams.FLAG_LOCAL_FOCUS_MODE,
            PixelFormat.TRANSLUCENT
        )
        windowManager.addView(view, params)

    }

    fun close() {
        try {

            windowManager.removeView(view)
            view.invalidate()
            (view.parent as ViewGroup).removeAllViews()
        } catch (e: Exception) {
            Log.d("Error2", e.toString())
        }
    }

    fun closeService(){
        context.stopService(Intent(context,AlertService::class.java))
    }
}