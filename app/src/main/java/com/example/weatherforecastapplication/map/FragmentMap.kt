package com.example.weatherforecastapplication.map

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.productmvvm.db.ConcreteLocalSource
import com.example.productmvvm.model.Repository
import com.example.productmvvm.network.WeatherClient
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.favourite.viewmodel.FavouriteViewModel
import com.example.weatherforecastapplication.favourite.viewmodel.FavouriteViewModelFactory
import com.example.weatherforecastapplication.model.Favourite
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.SupportMapFragment
import java.util.*


class FragmentMap : Fragment(){

    lateinit var locationMap: SharedPreferences
    lateinit var editorLocationMap: SharedPreferences.Editor
    lateinit var allFactory: FavouriteViewModelFactory
    lateinit var viewModel: FavouriteViewModel
    lateinit var buttonSave:Button

    lateinit var cityName:String
    lateinit var lat:String
    lateinit var long:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       val view :View = inflater.inflate(R.layout.fragment_map, container, false)
       val supportMapFragment : SupportMapFragment=getChildFragmentManager().findFragmentById(R.id.map_fragment) as SupportMapFragment
        supportMapFragment.getMapAsync(object : OnMapReadyCallback {
            override fun onMapReady(googleMap: GoogleMap) {
                googleMap.setOnMapClickListener {
                    val marker = MarkerOptions()
                    marker.position(it)
                    googleMap.clear()
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(it,5f))
                    googleMap.addMarker(marker)
                    Log.i(TAG, "onMapClick: ${it.latitude} KG ${it.longitude}")
                    locationMap= requireActivity().getSharedPreferences("LastLocationMap", Context.MODE_PRIVATE)
                    editorLocationMap=locationMap.edit()
                    long=it.longitude.toString()
                    lat=it.latitude.toString()
                    val geocoder: Geocoder

                    geocoder = Geocoder(requireContext(), Locale.getDefault())

                    editorLocationMap.putString("longitudeMap", it.longitude.toString()).commit()
                    editorLocationMap.putString("latitudeMap",it.latitude.toString()).commit()

                   // val cityName: String?
                    val Adress = geocoder.getFromLocation(it.latitude, it.longitude,2)
                    if(Adress!=null) {
                        cityName = Adress.get(0).adminArea
                        editorLocationMap.putString("cityNameMap", cityName).commit()
                        Log.i(TAG, "cityName: ${cityName}")

                    }else{
                        Toast.makeText(requireContext(),"i can't found country",Toast.LENGTH_LONG)
                       .show()
                       // editorLocationMap.putString("cityNameMap", "").commit()
                    }
                }
            }

        } )
        allFactory= FavouriteViewModelFactory(

            Repository.getInstance(
                WeatherClient.getInstance(), ConcreteLocalSource(requireContext())
            ))

        viewModel= ViewModelProvider(this,allFactory).get(FavouriteViewModel::class.java)

        buttonSave=view.findViewById(R.id.addToFav_btn)
        buttonSave.setOnClickListener {
            viewModel.insertFavourite(Favourite(cityName,long,lat))
            Navigation.findNavController(requireView()).navigate(R.id.action_fragmentMap2_to_fragmentFavouriteList)
        }

        return view
    }


}