package com.example.weatherforecastapplication.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.weatherforecastapplication.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class FragmentMap : Fragment(),OnMapReadyCallback {

    lateinit var gMap:GoogleMap
    lateinit var map:FrameLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onMapReady(p0: GoogleMap) {
        this.gMap=p0
        var mapIndia:LatLng= LatLng(33.0,-94.0)
        this.gMap.addMarker(MarkerOptions().position(mapIndia).title("GGGOOGLE MAP DONE"))
        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(mapIndia))
    }
}