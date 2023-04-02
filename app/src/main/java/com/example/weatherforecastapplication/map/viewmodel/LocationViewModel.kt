package com.example.weatherforecastapplication.map.viewmodel

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherforecastapplication.PERMISSION_ID
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.util.*

class LocationViewModel (var context:Context) : ViewModel() {

    /**
     * MutableLiveData private field to get/save location updated values
     */
  //  private val locationData = LocationLiveData(application)
    private val _location = MutableLiveData<Location>()
    val getLocationData: LiveData<Location>
        get() = _location




}






    //:ViewModel() {
//    private val _location = MutableLiveData<Location>()
//
//    val location: LiveData<Location>
//        get() = _location
//}

