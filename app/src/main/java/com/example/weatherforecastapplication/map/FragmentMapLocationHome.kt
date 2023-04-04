package com.example.weatherforecastapplication.map

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.WeatherViewModel
import com.example.weatherforecastapplication.WeatherViewModelFactory
import com.example.weatherforecastapplication.model.Repository
import com.example.weatherforecastapplication.network.ConcreteLocalSource
import com.example.weatherforecastapplication.network.WeatherClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*


class FragmentMapLocationHome : Fragment() {
    lateinit var allFactory: WeatherViewModelFactory
    lateinit var viewModel: WeatherViewModel

    lateinit var cityName:String
    lateinit var lat:String
    lateinit var long:String
    lateinit var location: SharedPreferences

    lateinit var editorLocation: SharedPreferences.Editor
    lateinit var buttonSave : Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view :View = inflater.inflate(R.layout.fragment_map_location_home, container, false)
        val supportMapFragment : SupportMapFragment =getChildFragmentManager().findFragmentById(R.id.map_fragment) as SupportMapFragment
        supportMapFragment.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {
                googleMap.setOnMapClickListener {
                    val marker = MarkerOptions()
                    marker.position(it)
                    googleMap.clear()
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it,5f))
                    googleMap.addMarker(marker)
                    Log.i(ContentValues.TAG, "onMapClick: ${it.latitude} KG ${it.longitude}")
                    location= requireActivity().getSharedPreferences("LastLocation", Context.MODE_PRIVATE)
                    editorLocation=location.edit()
                    long=it.longitude.toString()
                    lat=it.latitude.toString()
                    val geocoder: Geocoder

                    geocoder = Geocoder(requireContext(), Locale.getDefault())

//                    editorLocationMap.putString("longitudeMap", it.longitude.toString()).commit()
//                    editorLocationMap.putString("latitudeMap",it.latitude.toString()).commit()
                    editorLocation.putString("longitude",it.longitude.toString()).commit()
                    editorLocation.putString("latitude",it.latitude.toString()).commit()

                    // val cityName: String?
                    val Adress = geocoder.getFromLocation(it.latitude, it.longitude,2)
                    if(Adress!=null) {
                        cityName = Adress.get(0).adminArea
//                        editorLocationMap.putString("cityNameMap", cityName).commit()
//                        Log.i(ContentValues.TAG, "cityName: ${cityName}")
                        editorLocation.putString("cityName",cityName).commit()

                    }else{
                        Toast.makeText(requireContext(),"i can't found country", Toast.LENGTH_LONG)
                            .show()
                        // editorLocationMap.putString("cityNameMap", "").commit()
                    }
                }
            }

        } )
        allFactory= WeatherViewModelFactory(

            Repository.getInstance(
                WeatherClient.getInstance(), ConcreteLocalSource(requireContext())
            ))

        viewModel= ViewModelProvider(this,allFactory).get(WeatherViewModel::class.java)

        buttonSave=view.findViewById(R.id.addLocation)
        buttonSave.setOnClickListener {

              Navigation.findNavController(requireView()).navigate(R.id.action_fragmentMapLocationHome_to_fragmentHome)
          //  Navigation.findNavController(requireView()).navigateUp()
        }


        return view
    }
}