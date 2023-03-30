package com.example.weatherforecastapplication.alerts.view

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.weatherforecastapplication.databinding.FragmentAlertListBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar


class FragmentAlertList : Fragment() {

    lateinit var binding: FragmentAlertListBinding
    lateinit var picker : MaterialTimePicker
    private var calender  = Calendar.getInstance()
    lateinit var alarmManager : AlarmManager
    lateinit var pendingIntent : PendingIntent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // return inflater.inflate(R.layout.fragment_home, container, false)
        binding= FragmentAlertListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAddAlertsList.setOnClickListener{
            showTimePicker()
        }

    }

    fun setAlarm(){
        alarmManager = context?.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(),WeatherAlertReceiver::class.java)

        pendingIntent=PendingIntent.getBroadcast(requireContext(),0,intent,PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,calender.timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent)

        Toast.makeText(context,"Alarm set successfuly",Toast.LENGTH_SHORT).show()
    }

    fun showTimePicker(){
        picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm time")
                .build()

        fragmentManager?.let { picker.show(it,"SKY") }
        picker.addOnPositiveButtonClickListener{
            if(picker.hour >12){
                val text =String.format("%02d",picker.hour-12)+":"+String.format("%02d",picker.minute)+"PM"
            }else{
                String.format("%02d",picker.hour)+":"+String.format("%02d",picker.minute)+"AM"
            }
            calender = Calendar.getInstance()
            calender[Calendar.HOUR_OF_DAY]=picker.hour
            calender[Calendar.MINUTE] = picker.minute
            calender[Calendar.SECOND] = 0
            calender[Calendar.MILLISECOND] = 0
        }
        setAlarm()
    }

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val name : CharSequence="WeatherReminderChannel"
            val description = "Channel For Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("SKY",name,importance)
            channel.description=description
            val notificationManager = context?.getSystemService(NotificationManager::class.java) as NotificationManager?
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}