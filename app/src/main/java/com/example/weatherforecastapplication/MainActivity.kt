package com.example.weatherforecastapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.weatherforecastapplication.alerts.view.FragmentAlertList
import com.example.weatherforecastapplication.favourite.view.FragmentFavouriteList
import com.example.weatherforecastapplication.home.view.FragmentHome
import com.example.weatherforecastapplication.map.PERMISSION_ID
import com.example.weatherforecastapplication.settings.view.FragmentSettings
import com.google.android.gms.location.*
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener


class MainActivity : AppCompatActivity(),OnNavigationItemSelectedListener {

    //variable
    lateinit var drawerLayout:DrawerLayout
    lateinit var navigationView:NavigationView
    lateinit var toolbar: Toolbar

    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var location: SharedPreferences

    lateinit var editorLocation: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout=findViewById(R.id.drawer_layout)
        navigationView=findViewById(R.id.navigator_layout)
        toolbar=findViewById(R.id.toolbar)
        setTitle("")

        //toolBar
        setSupportActionBar(toolbar)

        //navigation Drawer menu
        navigationView.bringToFront()
        var toggle:ActionBarDrawerToggle=ActionBarDrawerToggle(this,drawerLayout,toolbar,
            R.string.OPEN_navigation,R.string.Close_navigation)

        toggle.isDrawerIndicatorEnabled=true

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)


        this.onBackPressedDispatcher.addCallback(onBackPressedCallback)

        /////// Location ////////////
        location= getSharedPreferences("LastLocation", Context.MODE_PRIVATE)
        editorLocation=location.edit()
        getLastLocation()
    }

    //handle back button
    val onBackPressedCallback = object: OnBackPressedCallback(true) {

        override fun handleOnBackPressed() {

            if(drawerLayout.isDrawerOpen(GravityCompat.START)){
                drawerLayout.closeDrawer(GravityCompat.START)
            }
            else{
                customExitDialog()
            }

        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)

        when(item.itemId){
            R.id.home->{
                setToolbarTitle("Home")
                changeFragment(FragmentHome())
            }
            R.id.favourite->{
                setToolbarTitle("Favourite")
                changeFragment(FragmentFavouriteList())
            }
            R.id.alter->{
                setToolbarTitle("Alter")
                changeFragment(FragmentAlertList())
            }
            R.id.setting->{
                setToolbarTitle("Setting")
                changeFragment(FragmentSettings())
            }

        }
        return true
    }

    fun setToolbarTitle(title:String){
        supportActionBar?.title=title
    }
    fun changeFragment(fragmentSelect:Fragment){
        val fragment=supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_contaner,fragmentSelect).commit()
    }

    fun customExitDialog() {
        // creating custom dialog
        val dialog = Dialog(this@MainActivity)

        // setting content view to dialog
        dialog.setContentView(R.layout.custom_exit_dialog)

        // getting reference of TextView
        val dialogButtonYes = dialog.findViewById(R.id.textViewYes) as TextView
        val dialogButtonNo = dialog.findViewById(R.id.textViewNo) as TextView

        // click listener for No
        dialogButtonNo.setOnClickListener { // dismiss the dialog
            dialog.dismiss()
        }
        // click listener for Yes
        dialogButtonYes.setOnClickListener { // dismiss the dialog and exit the exit
            dialog.dismiss()
            finish()
        }

        // show the exit dialog
        dialog.show()
    }

    override fun onResume() {
        super.onResume()
        if(checkPermissions()){
            getLastLocation()
        }
    }

    private fun checkPermissions():Boolean{
        val result =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED||
                    ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
        return result
    }

    @SuppressLint("MissingPermission")
    private fun getLastLocation(){
        if(checkPermissions()){
            if(isLocationEnabled()){
                requestNewLocationDate()
                Toast.makeText(this,"datttttta", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"Turn on location", Toast.LENGTH_LONG).show()
                val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        }
        else{
            requestPermissions()
        }
    }

    private fun isLocationEnabled():Boolean{

        val locationManager: LocationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationDate() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        mLocationRequest.setInterval(0)

        mFusedLocationClient= LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(mLocationRequest,mLocationCallback, Looper.myLooper())


    }

    private val mLocationCallback: LocationCallback =object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult) {
//            val geocoder: Geocoder
//
//            geocoder = Geocoder(applicationContext, Locale.getDefault())


            val mLastLocation: Location? =locationResult.lastLocation
            if (mLastLocation != null) {
                var textViewLogtiude=mLastLocation.longitude.toString()
                editorLocation.putString("longitude",textViewLogtiude).commit()
                Log.i(ContentValues.TAG, "longitude: ${mLastLocation.longitude.toString()}")
                println("longitude: ${mLastLocation.longitude.toString()}")
            }
            if (mLastLocation != null) {
                var textViewLatitude=mLastLocation.latitude.toString()
                editorLocation.putString("latitude",textViewLatitude).commit()
                Log.i(ContentValues.TAG, "latitude: ${mLastLocation.latitude.toString()}")
                println("latitude: ${mLastLocation.latitude.toString()}")
            }
//            if (mLastLocation != null&&mLastLocation != null) {
//                val addresses = geocoder.getFromLocation(mLastLocation.latitude, mLastLocation.longitude, 1)
//                val address = addresses!![0].getAddressLine(0)
//                textViewGeoCoder.text=address
//            }

        }
    }

    private fun requestPermissions(){
        ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }



}