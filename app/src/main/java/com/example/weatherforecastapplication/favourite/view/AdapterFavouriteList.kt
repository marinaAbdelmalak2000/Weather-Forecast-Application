package com.example.weatherforecastapplication.favourite.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.productmvvm.db.ConcreteLocalSource
import com.example.productmvvm.model.Repository
import com.example.productmvvm.network.WeatherClient
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.databinding.RowFavouriteListBinding
import com.example.weatherforecastapplication.favourite.viewmodel.FavouriteViewModel
import com.example.weatherforecastapplication.favourite.viewmodel.FavouriteViewModelFactory
import com.example.weatherforecastapplication.model.Favourite

class AdapterFavouriteList (private var favourites: List<Favourite>,var onDelete:OnDeleteClick) :
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

        binding.imageViewDelete.setOnClickListener{
            println("///////////////////////////////////dddddddddddddddddddddddddddddxfcgvhbjnkmnbvcdfcgv vhjdddd")
            Log.i(TAG, "imageViewDelete: ///////////// ")
            //deleteClick.deleteItem(favourites.get(position))
            //deleteItem(position)
            onDelete.deleteItem(favourites.get(position))
        }
        holder.binding.rowFavourite.setOnClickListener{
            Log.i(TAG, "onBindViewHolder: ///////////////////////////////debugggggggggggggggggggggggggggggg")
            onDelete.deleteItem(favourites.get(position))
        }

    }

    override fun getItemCount(): Int =favourites.size

    fun setData(value: List<Favourite>){
        this.favourites=value as List<Favourite>
        notifyDataSetChanged()
    }
    class ViewHolder(var binding: RowFavouriteListBinding): RecyclerView.ViewHolder(binding.root)

//    fun deleteItem(position: Int) {
//        val builder = AlertDialog.Builder(context)
//        builder.setTitle("Delete item")
//        builder.setMessage("Are you sure you want to delete this item?")
//        builder.setPositiveButton("Yes") { dialog, which ->
//            // Code to delete the item at the given position goes here
//        }
//        builder.setNegativeButton("No", null)
//        builder.show()
//    }


    }