package com.example.weatherforecastapplication.home.view

import android.R
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapplication.databinding.RowHourlyHomeBinding
import com.example.weatherforecastapplication.model.Hourly
import com.squareup.picasso.Picasso
import java.lang.String

class AdapterHourlyHome (private var hours: List<Hourly>) :
    RecyclerView.Adapter<AdapterHourlyHome.ViewHolder>() {

    lateinit var binding:RowHourlyHomeBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RowHourlyHomeBinding.inflate(inflater, parent, false)
        Log.i(ContentValues.TAG, "onCreateViewHolder: ")
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current=hours[position]
//        Picasso.get().load(current.thumbnail).into(holder.binding.imageView)
//        holder.binding.textViewTitle.text=current.title
//        holder.binding.textViewDescription.text=current.description
//        holder.binding.ratingBar.rating=current.rating as Float
//        holder.binding.textViewBrand.text=current.brand
//        holder.binding.textViewPrice.setText(String.valueOf(products.get(position).price))
//        holder.binding.buttonFav.setOnClickListener{ onProductClickLesener.onClick(products.get(position))
//
//        }
        holder.binding.txtView24hourDay.text="6 PM"
        holder.binding.txtViewTempPerHour.text="24 C"

    }

    override fun getItemCount(): Int =hours.size

    fun setData(value: List<Hourly>){
        this.hours=value as List<Hourly>
        notifyDataSetChanged()
    }
    class ViewHolder(var binding: RowHourlyHomeBinding): RecyclerView.ViewHolder(binding.root)
}