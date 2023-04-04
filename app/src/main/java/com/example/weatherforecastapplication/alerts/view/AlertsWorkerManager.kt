package com.example.weatherforecastapplication.alerts.view

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat

import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.*
import com.example.weatherforecastapplication.MainActivity
import com.example.weatherforecastapplication.model.CityAlarmList
import com.example.weatherforecastapplication.model.Repository
import com.example.weatherforecastapplication.network.ConcreteLocalSource
import com.example.weatherforecastapplication.network.WeatherClient
import com.example.weatherforecastapplication.utils.Constants
import kotlinx.coroutines.flow.first
import java.util.*
import java.util.concurrent.TimeUnit

class AlertsWorkerManager (private var context: Context, private var workParams: WorkerParameters)
    : CoroutineWorker(context, workParams){

    val repository=Repository.getInstance(WeatherClient.getInstance(), ConcreteLocalSource(context))

    override suspend fun doWork(): Result {

            if (!isStopped) {
                Log.i(TAG, "doWork: ALTERSWOrk Managerrrrrrrr ")
                val id = inputData.getLong("id", -1)
                Log.i(TAG, "doWork: ALTERSWOrk Managerrrrrrrr $id")
                getData(id.toInt())
            }
            return Result.success()

    }
    private suspend fun getData(id: Int) {

        //get data from room
        val currentWeather = repository.getStoredWeatherModel().first()
        val alert = repository.getOneAlert(id)
        if (checkTimeLimit(alert)) {
            val delay: Long = getDelay(alert)
            println("/////////delayyyyyyyy :::::::::${delay}")
            if (currentWeather?.alerts?.description.isNullOrEmpty()) {
                setOneTimeWorkManger(
                    delay,
                    alert.id,
                    currentWeather?.current?.weather?.get(0)?.description ?: "",
                )
                println("/////// setOneTimeWorkManger111111:::::::::::::::${currentWeather?.current?.weather?.get(0)?.description}")
              //  startAlertService(currentWeather?.current?.weather?.get(0)?.description!!)
            } else {
                println("/////// setOneTimeWorkManger222222:::::::::::::::${currentWeather?.alerts?.tags?.get(0)}")
                setOneTimeWorkManger(
                    delay,
                    alert.id,
                    currentWeather?.alerts?.tags?.get(0) ?:"",
                )
            }
        }
        else {
            println("/////////deleteAlterFromWorkManager**********")
            repository.deleteAlert(id)
            WorkManager.getInstance().cancelAllWorkByTag("$id")
        }

    }

    private fun setOneTimeWorkManger(delay: Long, id: Int?, description: String) {
        val data = Data.Builder()
        data.putString("description", description)

        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        Log.i(TAG, "setOneTimeWorkManger******: ${description}")
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(OneTimeWorkManager::class.java,)
            .setInitialDelay(delay, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "$id",
            ExistingWorkPolicy.REPLACE,
            oneTimeWorkRequest
        )
    }

    private fun checkTimeLimit(alert: CityAlarmList): Boolean {
        //currend time
        var dayNow=System.currentTimeMillis()
        println(dayNow)
        Log.i(TAG, "checkTimeLimit: ${alert.startDate}  ${alert.id}")

        return dayNow >= alert.startDate!! && dayNow <= alert.endDate!!
    }

    private fun getDelay(alert: CityAlarmList): Long {
//        val hour = TimeUnit.HOURS.toSeconds(Calendar.getInstance().get(Calendar.HOUR_OF_DAY).toLong())
//        val minute = TimeUnit.MINUTES.toSeconds(Calendar.getInstance().get(Calendar.MINUTE).toLong())
//        return alert.startTime!! - ((hour + minute) - (2 * 3600L))
        return (alert.startDate?.div(1000L))?.minus((alert.endDate?.div(1000L)!!)) ?: 0
    }


}