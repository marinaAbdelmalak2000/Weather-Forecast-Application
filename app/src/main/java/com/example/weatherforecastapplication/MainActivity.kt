package com.example.weatherforecastapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.weatherforecastapplication.model.CurrentLocation
import com.example.weatherforecastapplication.model.Repository
import com.example.weatherforecastapplication.network.ConcreteLocalSource
import com.example.weatherforecastapplication.network.WeatherClient

import com.example.weatherforecastapplication.utils.NetwarkInternet
import com.google.android.gms.location.*
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

import java.util.*


const val PERMISSION_ID =44

class MainActivity : AppCompatActivity() { //,OnNavigationItemSelectedListener


    private val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )


    private var doubleBackToExitPressedOnce = false

    lateinit var allFactory: WeatherViewModelFactory
    lateinit var viewModel: WeatherViewModel

    //variable
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var navController: NavController
    lateinit var toolbar: Toolbar

    lateinit var location: SharedPreferences
    lateinit var editorLocation: SharedPreferences.Editor

    val netwarkInternet = NetwarkInternet()

    lateinit var mFusedLocationClient: FusedLocationProviderClient

    lateinit var sharedPreference: SharedPreferences
    lateinit var address: String

    var lat: Double = 0.0
    var long: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        allFactory = WeatherViewModelFactory(

            Repository.getInstance(
                WeatherClient.getInstance(), ConcreteLocalSource(this)
            )
        )

        viewModel = ViewModelProvider(this, allFactory).get(WeatherViewModel::class.java)

        var language = viewModel.language
        if (language.equals("en")) {
            viewModel.setLocal(this, "en")
           // changeLanguage("en")

        } else {
            viewModel.setLocal(this, "ar")
          //  changeLanguage("ar")

        }

        drawerLayout = findViewById(R.id.drawer_layout)

        navigationView = findViewById(R.id.navigator_layout)
        toolbar = findViewById(R.id.toolbar)
        setTitle("")

        //toolBar
        setSupportActionBar(toolbar)

        //navigation Drawer menu
        navigationView.bringToFront()
        var toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.OPEN_navigation, R.string.Close_navigation
        )

        toggle.isDrawerIndicatorEnabled = true

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        // actionBar?.setDisplayHomeAsUpEnabled(true)

        location=getSharedPreferences("LastLocation", Context.MODE_PRIVATE)
        editorLocation=location.edit()
        if (netwarkInternet.isNetworkAvailable(this) == true) {
            getLastLocation()
        } else {
            val snackbar: Snackbar =
                Snackbar.make(navigationView, getString(R.string.not_netwark), Snackbar.LENGTH_INDEFINITE)
            val snackbarView = snackbar.view
            val textView =
                snackbarView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
            textView.maxLines = 5
            snackbar.show()
        }

        navController = findNavController(this, R.id.nav_host_fragment)
        setupWithNavController(navigationView, navController)

        this.onBackPressedDispatcher.addCallback(onBackPressedCallback)

        //////////////////////////////******///////////******///

        val checkLocationSetting = viewModel.indexLocationSetting



    }


    //handle back button
    val onBackPressedCallback = object : OnBackPressedCallback(true) {

        override fun handleOnBackPressed() {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else if (doubleBackToExitPressedOnce) {
                // Show confirmation dialog
                customExitDialog()
            } else {
                doubleBackToExitPressedOnce = true
                Handler(Looper.getMainLooper()).postDelayed(
                    { doubleBackToExitPressedOnce = false },
                    2000
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // creating custom dialog
    fun customExitDialog() {
        val dialog = Dialog(this@MainActivity)

        dialog.setContentView(R.layout.custom_exit_dialog)

        val dialogButtonYes = dialog.findViewById(R.id.textViewYes) as TextView
        val dialogButtonNo = dialog.findViewById(R.id.textViewNo) as TextView

        // click listener for No
        dialogButtonNo.setOnClickListener { // dismiss the dialog
            dialog.dismiss()
            doubleBackToExitPressedOnce = false
        }
        // click listener for Yes
        dialogButtonYes.setOnClickListener { // dismiss the dialog and exit the exit
            dialog.dismiss()
            super.onBackPressed()
            finish()
        }

        // show the exit dialog
        dialog.show()
    }

    override fun onResume() {
        super.onResume()
        if (checkPermissions())
            getLastLocation()
    }

    override fun onStart() {
        super.onStart()
        getLastLocation()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnable()) {
                requestNewLocationData()
            } else {
                Toast.makeText(this, "Turn on Location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }

        } else {
            requestPermissions()
        }
    }
    private fun checkPermissions(): Boolean {
        val result = ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
            this, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return result
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ), PERMISSION_ID
        )
    }

    private fun isLocationEnable(): Boolean {
        val locationManager: LocationManager =
            this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = com.google.android.gms.location.LocationRequest()
        mLocationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY)
        mLocationRequest.setInterval(0)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location? = locationResult.lastLocation
            lateinit var geoCoder: Geocoder
            //GPs
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)
            geoCoder = Geocoder(applicationContext, Locale.getDefault())

            if (mLastLocation != null) {
                lat = mLastLocation.latitude
                long = mLastLocation.longitude

                val addresses = geoCoder.getFromLocation(mLastLocation!!.getLatitude(), mLastLocation!!.getLongitude(), 1)
                val city = addresses!![0].locality
                val country = addresses[0].countryName
                address = country+ "/"+ city

                editorLocation.putString("longitude",long.toString()).commit()
                editorLocation.putString("latitude",lat.toString()).commit()
                editorLocation.putString("cityName", address).apply()

                Log.i(TAG, "GPS Main Activity: ")
            }

            mFusedLocationClient.removeLocationUpdates(this)
        }
    }

}

