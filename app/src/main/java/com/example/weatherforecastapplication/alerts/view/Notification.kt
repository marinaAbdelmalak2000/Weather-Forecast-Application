package com.example.weatherforecastapplication.alerts.view

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.weatherforecastapplication.MainActivity
import com.example.weatherforecastapplication.R

const val CHANNEL_IDNew = "19"
class Notification(var context: Context) {

    fun createNotification(title:String,content:String){
        createNotificationChannel()
        val intent= Intent(context, MainActivity::class.java)
        var pendingIntent: PendingIntent? = null
        pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(context, 0, intent, 0)
        }

        var builder: NotificationCompat.Builder=
            NotificationCompat.Builder(context,CHANNEL_IDNew)
                .setSmallIcon(R.drawable.skyblueyelloimage)
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManagerCompat= NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
        }
        notificationManagerCompat.notify(10,builder.build())
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = NotificationChannel(
                CHANNEL_IDNew,
                " Display Sky app",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = " Channel Sky"
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}