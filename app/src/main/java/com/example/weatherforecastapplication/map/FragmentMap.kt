package com.example.weatherforecastapplication.map

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.favourite.viewmodel.FavouriteViewModel
import com.example.weatherforecastapplication.favourite.viewmodel.FavouriteViewModelFactory
import com.example.weatherforecastapplication.model.Favourite
import com.example.weatherforecastapplication.model.Repository
import com.example.weatherforecastapplication.network.ConcreteLocalSource
import com.example.weatherforecastapplication.network.WeatherClient
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.IOException
import java.util.*


class FragmentMap : Fragment(){

    lateinit var locationMap: SharedPreferences
    lateinit var editorLocationMap: SharedPreferences.Editor
    lateinit var allFactory: FavouriteViewModelFactory
    lateinit var viewModel: FavouriteViewModel
    lateinit var buttonSave:FloatingActionButton

    lateinit var cityName:String
    lateinit var lat:String
    lateinit var long:String

    lateinit var searchEdit:EditText
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
          //  Navigation.findNavController(requireView()).navigate(R.id.action_fragmentMap2_to_fragmentFavouriteList)
            Navigation.findNavController(requireView()).navigateUp()
        }

//        searchEdit=view.findViewById(R.id.editTextMapSearch)
//        searchEdit.setOnEditorActionListener { textView, actionId, keyEvent ->
//            if ((actionId == EditorInfo.IME_ACTION_SEARCH)
//                || (actionId == EditorInfo.IME_ACTION_DONE) ||
//                (keyEvent.action === KeyEvent.ACTION_DOWN)
//                || (keyEvent.action === KeyEvent.KEYCODE_ENTER)
//            ) {
//
//                geoLocate()
//            }
//            false
//        }


            return view
            }

//    private fun performSearch(location: String) {
//        val geocoder = Geocoder(requireContext())
//        val addressList = geocoder.getFromLocationName(location, 1)
//        if (addressList.isNotEmpty()) {
//            val address = addressList[0]
//            val latLng = LatLng(address.latitude, address.longitude)
//            val googleMap = (findViewById<MapView>(R.id.map_fragment)).map
//            googleMap.addMarker(MarkerOptions().position(latLng).title(location))
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f))
//        } else {
//            Toast.makeText(requireContext(), "Location not found", Toast.LENGTH_SHORT).show()
//        }
//    }

//    fun search(){
//
//        binding.search.setOnEditorActionListener(OnEditorActionListener { textView, actionId, keyEvent ->
//            if ((actionId == EditorInfo.IME_ACTION_SEARCH)
//                || (actionId == EditorInfo.IME_ACTION_DONE) ||
//                (keyEvent.action === KeyEvent.ACTION_DOWN)
//                || (keyEvent.action === KeyEvent.KEYCODE_ENTER)
//            ) {
//
//                geoLocate()
//            }
//            false
//        })
//    }
    private fun geoLocate() {
        val searchString: String = searchEdit.getText().toString()
        val geocoder = Geocoder(requireContext())
        var list: List<Address>? = ArrayList()
        try {
            list = geocoder.getFromLocationName(searchString, 1)
           // setDialog(list?.get(0)!!.latitude,list?.get(0)!!.longitude,requireView())

        } catch (e: IOException) {
            Log.i("search error", "geoLocate: IOException: "+e.message.toString())
        }
        if (list!!.size > 0) {
            val address: Address = list[0]
            Log.i("searched adress", "geoLocate: found a location: " + address.toString())
        }
    }

//
//

}