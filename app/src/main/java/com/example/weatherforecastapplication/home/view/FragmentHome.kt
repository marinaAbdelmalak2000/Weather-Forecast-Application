package com.example.weatherforecastapplication.home.view

import android.Manifest
import android.annotation.SuppressLint
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
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.productmvvm.db.ConcreteLocalSource
import com.example.productmvvm.model.Repository
import com.example.productmvvm.network.WeatherClient
import com.example.weatherforecastapplication.PERMISSION_ID
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.WeatherViewModel
import com.example.weatherforecastapplication.WeatherViewModelFactory
import com.example.weatherforecastapplication.databinding.FragmentHomeBinding
import com.example.weatherforecastapplication.model.Daily
import com.example.weatherforecastapplication.model.Hourly
import com.example.weatherforecastapplication.network.ApiState
import com.example.weatherforecastapplication.utils.NetwarkInternet
import com.google.android.gms.location.*

import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class FragmentHome : Fragment() {

    lateinit var binding: FragmentHomeBinding

    lateinit var allFactory: WeatherViewModelFactory
    lateinit var viewModel: WeatherViewModel

    lateinit var recyclerAdapterHourHome: AdapterHourlyHome
    var hourList= mutableListOf<Hourly>()

    lateinit var recyclerAdapterDaysHome: AdapterDaysHome
    var daysList= mutableListOf<Daily>()

    val netwarkInternet= NetwarkInternet()

    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var location: SharedPreferences

    lateinit var editorLocation: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



       // return inflater.inflate(R.layout.fragment_home, container, false)
        binding= FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //24 per day
       // var temp=viewModel.checkTemp
        recyclerAdapterHourHome= AdapterHourlyHome(hourList)
        //initialization
        binding.recyclerViewHourHome.adapter=recyclerAdapterHourHome

        //days
        recyclerAdapterDaysHome= AdapterDaysHome(daysList)
        //initialization
        binding.recyclerViewDaysHome.adapter=recyclerAdapterDaysHome


        allFactory= WeatherViewModelFactory(

            Repository.getInstance(
                WeatherClient.getInstance(), ConcreteLocalSource(requireContext())
            ))

        viewModel= ViewModelProvider(this,allFactory).get(WeatherViewModel::class.java)

//       //location
       location= requireActivity().getSharedPreferences("LastLocationCurrent", Context.MODE_PRIVATE)
//       editorLocation=location.edit()



      //  val locationMap:SharedPreferences= requireActivity().getSharedPreferences("LastLocationMap", Context.MODE_PRIVATE)
     //  val sharedLocation: SharedPreferences = requireActivity().getSharedPreferences("LastLocation", Context.MODE_PRIVATE)
       val longitudecurrent=location.getString("longitude","20.25")
       val latitudecurrent=location.getString("latitude","30.25")
        val cityName=location.getString("cityName","null")
      //  val cityNameMap=sharedLocation.getString("cityNameMap","null")

       lifecycleScope.launch {

           viewModel.uiState.collectLatest { uiState ->when (uiState) {

               is ApiState.Success -> {
                   binding.progressBarHome.visibility = View.GONE
                   binding.recyclerViewHourHome.visibility = View.VISIBLE
                   binding.recyclerViewDaysHome.visibility = View.VISIBLE
                   binding.textViewCityNameHome.visibility = View.VISIBLE
                   binding.textViewCityNameHome.text=cityName
//                   val checkLocationName=viewModel.indexLocationSetting
//                   if(checkLocationName==1){
//                       binding.textViewCityNameHome.text=cityNameMap
//                   }else{
//                       binding.textViewCityNameHome.text=cityName
//                   }


                   println("//////**** Current ****//////")

                   var weatherList = uiState.data.current.weather
                   for (i in 0..weatherList.size - 1) {
                       var weatherDescription = weatherList.get(i).description
                       var weatherIcon = weatherList.get(i).icon
                       binding.textViewDescriptionTodayHome.text = "$weatherDescription"
                       changeIconWeather(weatherIcon)

                   }
                   binding.textViewCloud.text =
                       "${uiState.data.current.clouds.toString()} %"

                   ////////////////////check temp convert celsius to fahrenheit and Kelvin /////////////////////////////

                   if (viewModel.checkTemp.equals("F")) {
                       //°F = (°C × 9/5) + 32
                       var convertDataTempF = (uiState.data.current.temp * (9 / 5)) + 32
                       val formattedDouble = String.format("%.2f", convertDataTempF)
                       binding.textViewTempTodayHome.text =
                           "${formattedDouble.toString()} " + "°F"
                   } else if (viewModel.checkTemp.equals("K")) {
                       //Kelvin = Celsius + 273.15
                       var convertDataTempK = uiState.data.current.temp + 273.15
                       val formattedDouble = String.format("%.2f", convertDataTempK)
                       binding.textViewTempTodayHome.text =
                           "${formattedDouble.toString()} " + "°K"
                   } else {
                       val formattedDouble =
                           String.format("%.2f", uiState.data.current.temp)
                       binding.textViewTempTodayHome.text =
                           "${formattedDouble.toString()}°C"
                   }

                   //////////////////////////////////////////////////////////////////////////////////////

                   binding.textViewHumidity.text =
                       uiState.data.current.humidity.toString() + "%"
                   binding.textViewPressure.text = "${uiState.data.current.pressure} hpa"


                   ////////////////////check speed miles/hour or meter/sec   /////////////////////////////

                   if (viewModel.checkSpeed.equals("miles/hour")) {
                       //convert meter/sec to miles/hour ==>  1 m/s = 2.23694 mph
                       var convertDataSpeed = uiState.data.current.wind_speed * 2.23694
                       val formattedDouble = String.format("%.2f", convertDataSpeed)
                       binding.textViewWindSpeed.text =
                           "${formattedDouble.toString()}miles/hour"
                   }
                   if (viewModel.checkSpeed.equals("meter/sec")) {
                       val formattedDouble =
                           String.format("%.2f", uiState.data.current.wind_speed)
                       binding.textViewWindSpeed.text =
                           "${formattedDouble.toString()}meter/sec"
                   }

                   ///////////////////////////////////////////////////////////////////////////////////////

                   //  println("/////alerts///////::::::: ${uiState.data.alerts.toString()} ")

                   val currentDate = uiState.data.current.dt
                   // yyyy-MM-dd
                   val dateTime = Date(currentDate * 1000)
                   val formattedDate =
                       SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dateTime)
                   //  print("////Current Date:  ${formattedDate}  ")
                   val formateTime = SimpleDateFormat(
                       "hh:mm a",
                       Locale.ENGLISH
                   ).format(Date(currentDate * 1000))
//                    println("/////Current Time-12-hour clock:  ${ SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
//                        Date(currentDate*1000)
//                    )}")

                   binding.textViewCurrentDateTimeHome.text =
                       "${formattedDate}   $formateTime "

                   val sunrise = uiState.data.current.sunrise
                   val sunriseDate = Date(sunrise * 1000)
//                    print("/////sunrise Day/////////// ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(sunriseDate)} ")
//                    println("/////sunrise Time-12-hour clock:  ${ SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(
//                        Date(sunrise*1000)
//                    )}")
                   val sunset = Date(uiState.data.current.sunset * 1000)
//                    print("/////sunset Day//////////////// ${SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(sunset)} ")
//                    println("/////sunset Time:  ${ SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(sunset)}")


                   binding.textViewUVI.text = "${uiState.data.current.uvi.toString()}"


                   println("//////**** Dayliy ****//////")

                   var dailyList = uiState.data.daily
                   var temp = viewModel.checkTemp
                   recyclerAdapterDaysHome.setData(dailyList, temp)
                   recyclerAdapterDaysHome.notifyDataSetChanged()


                   println("//////**** hourly ****//////")

                   var hourlyList = uiState.data.hourly
                   //  var temp=viewModel.checkTemp
                   println("temp hourly send $temp")
                   recyclerAdapterHourHome.setData(hourlyList, temp)
                   recyclerAdapterHourHome.notifyDataSetChanged()


               }
               is ApiState.Loading->{
                   println("initial")
                  // getLastLocation()
                   binding.progressBarHome?.visibility = View.VISIBLE
                   binding.recyclerViewHourHome.visibility = View.GONE
                   binding.recyclerViewDaysHome.visibility = View.GONE
                   binding.textViewCityNameHome.visibility = View.GONE
               }

               else ->{
                   // viewModel.refreshData()
                   binding.progressBarHome?.visibility = View.GONE
                   //  println("Check your netwark ${viewModel.refreshData()}")
                   Toast.makeText(
                       requireContext(),
                       "Check your netwark",
                       Toast.LENGTH_SHORT
                   ).show()

               }


           }}
       }
//       val longcurentLocation=sharedLocation.getString("longitude","null")
//       val latcurentLocation=sharedLocation.getString("latitude","null")
//       val longitudeMap=locationMap.getString("longitudeMap","30.20")
//       val latitudeMap=locationMap.getString("latitudeMap","30.55")

               if(netwarkInternet.isNetworkAvailable(context)==true){
                   var language=viewModel.language
                   val unit=viewModel.unit
//                   val lon=viewModel.longitude !!
//                   val lat=viewModel.latitude  !!
                   viewModel.allWeatherNetwork(longitudecurrent!!,latitudecurrent!!,"",unit,language)
//                   val checkLocation=viewModel.indexLocationSetting
//                   if(checkLocation==1){
//                       //map
 //                      viewModel.allWeatherNetwork(latitudeMap!!,longitudeMap!!,"",unit,language)
//                   }
//                   else{
//                       //current
//                       viewModel.allWeatherNetwork(latcurentLocation!!,longcurentLocation!!,"",unit,language)
//                   }

               }

                else{
                     viewModel.getLocalWeatherModel()
                   Log.i(TAG, "viewModel.getLocalWeatherModel(): "+viewModel.getLocalWeatherModel().toString())

                }



    }


//    override fun onResume() {
//        super.onResume()
//        getLastLocation()
//    }
//
//
//    private fun checkPermissions():Boolean{
//        val result =
//            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED||
//                    ActivityCompat.checkSelfPermission(requireContext(),
//                        Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED
//        return result
//    }
//
//    @SuppressLint("MissingPermission")
//    private fun getLastLocation(){
//        if(checkPermissions()){
//            if(isLocationEnabled()){
//                requestNewLocationDate()
//                Toast.makeText(requireContext(),"datttttta", Toast.LENGTH_LONG).show()
//            }
//            else{
//                Toast.makeText(requireContext(),"Turn on location", Toast.LENGTH_LONG).show()
//                val intent= Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                startActivity(intent)
//            }
//        }
//        else{
//            requestPermissions()
//        }
//    }
//
//    private fun isLocationEnabled():Boolean{
//
//        val locationManager: LocationManager =requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(
//            LocationManager.NETWORK_PROVIDER)
//    }
//
//    @SuppressLint("MissingPermission")
//    private fun requestNewLocationDate() {
//        val mLocationRequest = LocationRequest()
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//        mLocationRequest.setInterval(0)
//
//        mFusedLocationClient= LocationServices.getFusedLocationProviderClient(requireContext())
//        mFusedLocationClient.requestLocationUpdates(mLocationRequest,mLocationCallback, Looper.myLooper())
//
//
//    }
//
//    private val mLocationCallback: LocationCallback =object : LocationCallback(){
//        override fun onLocationResult(locationResult: LocationResult) {
////            var geocoder: Geocoder
////            geocoder = Geocoder(requireContext().applicationContext, Locale.getDefault())
//
//
//            val mLastLocation: Location? =locationResult.lastLocation
//            if (mLastLocation != null) {
//                var textViewLogtiude=mLastLocation.longitude.toString()
//                editorLocation.putString("longitude",textViewLogtiude).commit()
//                Log.i(TAG, "longitude: ${mLastLocation.longitude.toString()}")
//                println("longitude: ${mLastLocation.longitude.toString()}")
//            }
//            if (mLastLocation != null) {
//                var textViewLatitude=mLastLocation.latitude.toString()
//                editorLocation.putString("latitude",textViewLatitude).commit()
//                Log.i(TAG, "latitude: ${mLastLocation.latitude.toString()}")
//                println("latitude: ${mLastLocation.latitude.toString()}")
//            }
//  /*          if (mLastLocation != null&&mLastLocation != null) {
////                val addresses = geocoder.getFromLocation(mLastLocation.latitude, mLastLocation.longitude, 1)
////                val address = addresses!![0].getAddressLine(0)
//                val cityName: String?
//                // val geoCoder = Geocoder(requireContext(), Locale.getDefault())
//                val Adress = geocoder.getFromLocation(mLastLocation.latitude, mLastLocation.longitude,2)
//
//                cityName = Adress?.get(0)?.adminArea
//                editorLocation.putString("cityName",cityName).commit()
//
//            }*/
//
//        }
//    }
//
//    private fun requestPermissions(){
//        ActivityCompat.requestPermissions(requireActivity(), arrayOf(
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_FINE_LOCATION), PERMISSION_ID
//        )
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//

    fun changeIconWeather(iconapi:String) {
        when(iconapi){
            "01d","01n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.sun_clear_icon) //clear sky
            "02d","02n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.img_9)   //few clouds
            "03d","03n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.img_10)  //scattered clouds
            "04d","04n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.img_7)   //broken clouds
            "09d","09n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.img_14)  //shower rain
            "10d","10n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.img_13)  //rain
            "11d","11n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.img_6)   //thunderstorm
            "13d","13n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.img_2) //snow
            "50d","50n" -> binding.imageViewIconTodayHome.setBackgroundResource(R.drawable.img_17) //mist
        }

    }

}