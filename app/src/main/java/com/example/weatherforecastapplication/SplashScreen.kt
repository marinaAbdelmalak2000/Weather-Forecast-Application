package com.example.weatherforecastapplication

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import com.google.android.gms.location.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class SplashScreen : AppCompatActivity()  {

    lateinit var lottieAnimationView: LottieAnimationView
    lateinit var lottieAnimationViewPlane:LottieAnimationView
    lateinit var lottieAnimationViewCloudDown: LottieAnimationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        lottieAnimationView=findViewById(R.id.splashLottieCloud)
        lottieAnimationViewPlane=findViewById(R.id.splashLottieplane)
        lottieAnimationViewCloudDown=findViewById(R.id.splashLottieCloudDown)

        lifecycleScope.launch {
           val job= launch {
                lottieAnimationViewCloudDown.playAnimation()
                lottieAnimationView.playAnimation()
                // lottieAnimationView.playSoundEffect()
                delay(1000)
                lottieAnimationViewPlane.playAnimation()
            }
            delay(5000)
            job.cancel()

            withContext(Dispatchers.Main){
                val intent = Intent(this@SplashScreen, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }


}