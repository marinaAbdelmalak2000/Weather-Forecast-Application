package com.example.weatherforecastapplication.home.view

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapplication.databinding.RowDaysHomeBinding
import com.example.weatherforecastapplication.model.Daily
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AdapterDaysHome (private var days: List<Daily>) :
    RecyclerView.Adapter<AdapterDaysHome.ViewHolder>() {

    lateinit var binding: RowDaysHomeBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RowDaysHomeBinding.inflate(inflater, parent, false)
        Log.i(ContentValues.TAG, "onCreateViewHolder: ")
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current=days[position]
//        Picasso.get().load(current.thumbnail).into(holder.binding.imageView)
//        holder.binding.textViewTitle.text=current.title
//        holder.binding.textViewDescription.text=current.description
//        holder.binding.ratingBar.rating=current.rating as Float
//        holder.binding.textViewBrand.text=current.brand
//        holder.binding.textViewPrice.setText(String.valueOf(products.get(position).price))
//        holder.binding.buttonFav.setOnClickListener{ onProductClickLesener.onClick(products.get(position))
//
//        }


        holder.binding.textViewTempDayHome.text="${current.temp.min}/${current.temp.max}"
        var dailyDay=current.dt
        var currentDailyDay= Date(dailyDay * 1000)
        val df: DateFormat = SimpleDateFormat("EEEE") //yyyy-MM-E //uuuu-MM-EEE //EEEEEEE
        holder.binding.textViewDayNameHome.text="${df.format(currentDailyDay)}"




    }

    override fun getItemCount(): Int =days.size

    fun setData(value: List<Daily>){
        this.days=value as List<Daily>
        notifyDataSetChanged()
    }
    class ViewHolder(var binding: RowDaysHomeBinding): RecyclerView.ViewHolder(binding.root)
}