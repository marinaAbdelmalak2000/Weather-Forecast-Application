package com.example.weatherforecastapplication.alerts.view

import android.app.*
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.weatherforecastapplication.MainActivity
import com.example.weatherforecastapplication.R

const val CHANNEL_ID = 1
private const val FOREGROUND_ID = 2
class AlertService : Service() {

    private var notificationManager: NotificationManager? = null
    var window: Window? = null

    override fun onBind(intent: Intent): IBinder ?{
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
         super.onStartCommand(intent, flags, startId)

            //get description from work manager
            val description = intent?.getStringExtra("description")
            createNotificationChannel()
            startForeground(FOREGROUND_ID, showNotificationSettings(description!!))
            //start window manger
            if (Settings.canDrawOverlays(this)) {
                window = Window(this, description)
                window!!.createWindow()
            }
         return START_NOT_STICKY
    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val description: String = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("$CHANNEL_ID", name, importance)
            channel.enableVibration(true)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotificationSettings(description: String): Notification {
        val intent = Intent(this, MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_MUTABLE)
        val builder = NotificationCompat.Builder(applicationContext, "$CHANNEL_ID")
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
        return builder as Notification
    }

}