package com.example.weatherforecastapplication.favourite.view


import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation

import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapplication.R

import com.example.weatherforecastapplication.databinding.RowFavouriteListBinding

import com.example.weatherforecastapplication.model.Favourite

class AdapterFavouriteList (private var favourites: List<Favourite>,private val onDelete:OnDeleteClick) :
    RecyclerView.Adapter<AdapterFavouriteList.ViewHolder>() {

    private var listener   = onDelete

    lateinit var binding: RowFavouriteListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RowFavouriteListBinding.inflate(inflater, parent, false)
        Log.i(TAG, "onCreateViewHolder: ")

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val current=favourites[position]

        holder.binding.txtViewNameFavouriteList.text=current.CityName.toString()

        binding.imgViewDeleteFavorite.setOnClickListener{
            println("///////////////////////////////////dddddddddddddddddddddddddddddxfcgvhbjnkmnbvcdfcgv vhjdddd")
            Log.i(TAG, "imageViewDelete: ///////////// ")
            listener.deleteItem(favourites.get(position))
        }
        holder.binding.rowFavourite.setOnClickListener{
            Log.i(TAG, "onBindViewHolder: ///////////////////////////////debugggggggggggggggggggggggggggggg")
            listener.setData(current)
        }

    }

    override fun getItemCount(): Int =favourites.size

    fun setData(value: List<Favourite>){
        this.favourites=value as List<Favourite>
        notifyDataSetChanged()
    }
    class ViewHolder(var binding: RowFavouriteListBinding): RecyclerView.ViewHolder(binding.root)


    }