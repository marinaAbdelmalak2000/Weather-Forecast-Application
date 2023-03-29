package com.example.weatherforecastapplication.favourite.view

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapplication.databinding.RowFavouriteListBinding
import com.example.weatherforecastapplication.model.Favourite

class AdapterFavouriteList (private var favourites: List<Favourite>) :
    RecyclerView.Adapter<AdapterFavouriteList.ViewHolder>() {

    lateinit var binding: RowFavouriteListBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RowFavouriteListBinding.inflate(inflater, parent, false)
        Log.i(ContentValues.TAG, "onCreateViewHolder: ")
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current=favourites[position]

         holder.binding.textViewNameCityFavouriteList.text=current.CityName.toString()
         println(current.CityName.get(3)+"/////"+current.id)
        Log.i(TAG, "debug/////onBindViewHolder: ${favourites.size} /////////  ${favourites.get(3)}")

    }

    override fun getItemCount(): Int =favourites.size

    fun setData(value: List<Favourite>){
        this.favourites=value as List<Favourite>
        notifyDataSetChanged()
    }
    class ViewHolder(var binding: RowFavouriteListBinding): RecyclerView.ViewHolder(binding.root)
}