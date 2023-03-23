package com.example.weatherforecastapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieAnimationView
import kotlinx.coroutines.*
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

        // Handler().postDelayed({
//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, 5000) // 3000 is the delayed time in milliseconds.
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