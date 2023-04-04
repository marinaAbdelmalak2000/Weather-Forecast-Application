package com.example.weatherforecastapplication.alerts.view

import android.app.*
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider

import com.example.weatherforecastapplication.alerts.viewmodel.AlertViewModel
import com.example.weatherforecastapplication.alerts.viewmodel.AlertViewModelFactory
import com.example.weatherforecastapplication.databinding.FragmentAlertsBinding
import com.example.weatherforecastapplication.model.CityAlarmList
import java.text.SimpleDateFormat
import java.util.*
import android.provider.Settings
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startForegroundService
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import com.example.weatherforecastapplication.MainActivity
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.model.Repository
import com.example.weatherforecastapplication.network.ConcreteLocalSource
import com.example.weatherforecastapplication.network.WeatherClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


const val CHANNEL_ID = 1
private const val FOREGROUND_ID = 2
class FragmentAlerts : DialogFragment() {

    lateinit var binding:FragmentAlertsBinding
    lateinit var allFactory: AlertViewModelFactory
    lateinit var viewModel: AlertViewModel

     var hourStart=0
     var minutsStart=0
    lateinit var timeAlert:String

     var  isClick :Boolean =false

    // AlertModel
    private  var cityAlarmList: CityAlarmList= CityAlarmList(0,0,0,0,0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAlertsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allFactory= AlertViewModelFactory(

            Repository.getInstance(
                WeatherClient.getInstance(), ConcreteLocalSource(requireContext())
            ))

        viewModel= ViewModelProvider(this,allFactory).get(AlertViewModel::class.java)
        binding.textViewFromTime.setOnClickListener {

            setAlarmDateAndTime{(timeInMillis, dateInMillis) ->


                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dateInMillis)
                val formateTime = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(timeInMillis ))

                binding.editTextFromTime.text="${formattedDate} ${formateTime}"
                cityAlarmList.startDate=dateInMillis
                cityAlarmList.startTime=timeInMillis
                cityAlarmList.endTime=timeInMillis
                timeAlert=formateTime
                Log.i(TAG, "onViewCreated: ${formattedDate} ${formateTime} ///////${cityAlarmList.startTime} ")

            }

        }
        binding.textViewToTime.setOnClickListener {

            setAlarmEndDate{
                val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(it)
                binding.editTextToTime.text = "${formattedDate} ${timeAlert}"
                cityAlarmList.endDate = it
            }

        }
        binding.textViewSaveAlarm.setOnClickListener{

            viewModel.insertAlert(cityAlarmList)

            dismiss()
        }

        // observe Insert
        lifecycleScope.launch {
            viewModel.uiStateInsert.collect{
                setPeriodWorkManger(it)

                println("work open //////////$it")
            }
        }


       


        val sharedPreferences=requireActivity().getSharedPreferences("AlertSitting",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        isClick=sharedPreferences.getBoolean("isClick",false)
        if(isClick){
            binding.radioButtonNotification.isChecked=false
            binding.radioButtonAlarm.isChecked=true
        }else{
            binding.radioButtonNotification.isChecked=true
            binding.radioButtonAlarm.isChecked=false
        }

        binding.radioButtonNotification.setOnCheckedChangeListener{_, isClick->
            if(isClick){
                editor.putBoolean("isClick",false)
                editor.apply()
            }
        }
        binding.radioButtonAlarm.setOnCheckedChangeListener{_, isClick->
            if(isClick){
                editor.putBoolean("isClick",true)
                editor.apply()
                //go to display over others apps
                checkOverlayPermission()
            }
        }
    }

    private fun setAlarmDateAndTime(callback: (Pair<Long, Long>) -> Unit) {
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                0,
                { _, year, month, day ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH, day)
                    TimePickerDialog(
                        requireContext(),
                        0,
                        { _, hour, minute ->
                            this.set(Calendar.HOUR_OF_DAY, hour)
                            this.set(Calendar.MINUTE, minute)
                            callback(this.timeInMillis to this.timeInMillis.dateToLong())
                        },
                        this.get(Calendar.HOUR_OF_DAY),
                        this.get(Calendar.MINUTE),
                        false
                    ).show()
                },
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.minDate = Calendar.getInstance().timeInMillis
            datePickerDialog.show()
        }
    }

    // Extension function to convert a date to a long value
    fun Long.dateToLong(): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = this
        return calendar.apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
    private fun setAlarmEndDate(callback: (Long) -> Unit) {
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                0,
                { _, year, month, day ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH, day)
                    callback(this.timeInMillis)
                },
                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.datePicker.minDate = Calendar.getInstance().timeInMillis
            datePickerDialog.show()
        }
    }


    fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(context)) {
                // send user to the device settings
                val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                startActivity(myIntent)
            }
        }
    }

    private fun setPeriodWorkManger(id: Long) {

        val data = Data.Builder()
        data.putLong("id", id)

        Log.i(TAG, "setPeriodWorkManger: id $id data ${data}")

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val periodicWorkRequest = PeriodicWorkRequest.Builder(
            AlertsWorkerManager::class.java,
            24, TimeUnit.HOURS
        )
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        WorkManager.getInstance(requireContext()).enqueueUniquePeriodicWork(
            "$id",
            ExistingPeriodicWorkPolicy.REPLACE,
            periodicWorkRequest
        )
       // var diff = (endDate.timeInMillis / 1000L) - (startDate.timeInMillis / 1000L)

//        val inputData = Data.Builder()
//            .putString("title", "Weather")
//            .putString("content", "current weather statue")
//            .putBoolean("typeAlert", isClick)
//            .build()
//
//
//        val fireAlertConstraints = Constraints.Builder()
//            .setRequiredNetworkType(NetworkType.CONNECTED)
//            .build()
//        val oneTimeWorkRequest = OneTimeWorkRequestBuilder<WorkerManager>()
//            .setInitialDelay(15, TimeUnit.MINUTES)
//            .setInputData(inputData)
//            .setConstraints(fireAlertConstraints)
//            .build()
//        WorkManager.getInstance(requireContext()).enqueue(oneTimeWorkRequest)
    }




}