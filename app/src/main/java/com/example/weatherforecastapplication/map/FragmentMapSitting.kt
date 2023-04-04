package com.example.weatherforecastapplication.map

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.WeatherViewModel
import com.example.weatherforecastapplication.WeatherViewModelFactory
import com.example.weatherforecastapplication.favourite.viewmodel.FavouriteViewModel
import com.example.weatherforecastapplication.favourite.viewmodel.FavouriteViewModelFactory
import com.example.weatherforecastapplication.map.viewmodel.LocationViewModel
import com.example.weatherforecastapplication.model.Favourite
import com.example.weatherforecastapplication.model.Repository
import com.example.weatherforecastapplication.network.ConcreteLocalSource
import com.example.weatherforecastapplication.network.WeatherClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


class FragmentMapSitting : Fragment() {

//    private lateinit var locationViewModel: LocationViewModel
//
//    lateinit var mFusedLocationClient: FusedLocationProviderClient
//  //  lateinit var location: SharedPreferences
//
//   // lateinit var editorLocation: SharedPreferences.Editor
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_gps, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
////        location= requireActivity().getSharedPreferences("LastLocation", Context.MODE_PRIVATE)
////        editorLocation=location.edit()
////        getLastLocation()
//
//        locationViewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
//
//        // Request location updates
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//
//            return
//        }
//        mFusedLocationClient.requestLocationUpdates(
//            locationRequest,
//            locationCallback,
//            Looper.getMainLooper()
//        )
//
//    }
//
//    private val locationRequest = LocationRequest.create().apply {
//        interval = 10000
//        fastestInterval = 5000
//        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//    }
//    private val locationCallback : LocationCallback= object : LocationCallback() {
//        override fun onLocationResult(locationResult: LocationResult) {
//            locationResult ?: return
//            for (location in locationResult.locations) {
//                //locationViewModel.location.value = location
//
//            }
//        }
//    }
//
//
//    override fun onResume() {
//        super.onResume()
//      //  getLastLocation()
//    }
//
//
////    private fun checkPermissions():Boolean{
////        val result =
////            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED||
////                    ActivityCompat.checkSelfPermission(requireContext(),
////                        Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
////        return result
////    }
////
////    @SuppressLint("MissingPermission")
////    private fun getLastLocation(){
////        if(checkPermissions()){
////            if(isLocationEnabled()){
////                requestNewLocationDate()
////                Toast.makeText(requireContext(),"datttttta", Toast.LENGTH_LONG).show()
////            }
////            else{
////                Toast.makeText(requireContext(),"Turn on location", Toast.LENGTH_LONG).show()
////                val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
////                startActivity(intent)
////            }
////        }
////        else{
////            requestPermissions()
////        }
////    }
////
////    private fun isLocationEnabled():Boolean{
////
////        val locationManager: LocationManager =requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
////        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(
////            LocationManager.NETWORK_PROVIDER)
////    }
//
////    @SuppressLint("MissingPermission")
////    private fun requestNewLocationDate() {
////        val mLocationRequest = LocationRequest()
////        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
////        mLocationRequest.setInterval(0)
////
////        mFusedLocationClient= LocationServices.getFusedLocationProviderClient(requireContext())
////        mFusedLocationClient.requestLocationUpdates(mLocationRequest,mLocationCallback, Looper.myLooper())
////
////
////    }
//
////    private val mLocationCallback: LocationCallback =object : LocationCallback(){
////        override fun onLocationResult(locationResult: LocationResult) {
////            val geocoder: Geocoder
////
////            geocoder = Geocoder(requireActivity(), Locale.getDefault())
////
////
////            val mLastLocation: Location? =locationResult.lastLocation
////            if (mLastLocation != null) {
////                var textViewLogtiude=mLastLocation.longitude.toString()
////                editorLocation.putString("longitude",textViewLogtiude).commit()
////                Log.i(ContentValues.TAG, "longitude: ${mLastLocation.longitude.toString()}")
////                println("longitude: ${mLastLocation.longitude.toString()}")
////            }
////            if (mLastLocation != null) {
////                var textViewLatitude=mLastLocation.latitude.toString()
////                editorLocation.putString("latitude",textViewLatitude).commit()
////                Log.i(ContentValues.TAG, "latitude: ${mLastLocation.latitude.toString()}")
////                println("latitude: ${mLastLocation.latitude.toString()}")
////            }
////            if (mLastLocation != null&&mLastLocation != null) {
//////                val addresses = geocoder.getFromLocation(mLastLocation.latitude, mLastLocation.longitude, 1)
//////                val address = addresses!![0].getAddressLine(0)
////                val cityName: String?
////                // val geoCoder = Geocoder(requireContext(), Locale.getDefault())
////                val Adress = geocoder.getFromLocation(mLastLocation.latitude, mLastLocation.longitude,2)
////
////                cityName = Adress?.get(0)?.adminArea
////                editorLocation.putString("cityName",cityName).commit()
////
////            }
////
////        }
////    }
//
////    private fun requestPermissions(){
////        ActivityCompat.requestPermissions(requireActivity(), arrayOf(
////            Manifest.permission.ACCESS_COARSE_LOCATION,
////            Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID
////        )
////    }
////
////    override fun onRequestPermissionsResult(
////        requestCode: Int,
////        permissions: Array<out String>,
////        grantResults: IntArray
////    ) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
////    }
//
//


}