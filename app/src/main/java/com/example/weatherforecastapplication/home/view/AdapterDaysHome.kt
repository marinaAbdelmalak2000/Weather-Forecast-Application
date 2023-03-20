package com.example.weatherforecastapplication.home.view

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapplication.databinding.RowDaysHomeBinding
import com.example.weatherforecastapplication.model.Daily
import com.squareup.picasso.Picasso
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

        var iconName=current.weather.get(0).icon
        when(iconName){
            "01d","01n" -> binding.imageViewIconDayHome.setBackgroundResource(com.example.weatherforecastapplication.R.drawable.img)     //clear sky
            "02d","02n" -> binding.imageViewIconDayHome.setBackgroundResource(com.example.weatherforecastapplication.R.drawable.img_9)   //few clouds
            "03d","03n" -> binding.imageViewIconDayHome.setBackgroundResource(com.example.weatherforecastapplication.R.drawable.img_10)  //scattered clouds
            "04d","04n" -> binding.imageViewIconDayHome.setBackgroundResource(com.example.weatherforecastapplication.R.drawable.img_7)   //broken clouds
            "09d","09n" -> binding.imageViewIconDayHome.setBackgroundResource(com.example.weatherforecastapplication.R.drawable.img_16)  //shower rain
            "10d","10n" -> binding.imageViewIconDayHome.setBackgroundResource(com.example.weatherforecastapplication.R.drawable.img_12)  //rain
            "11d","11n" -> binding.imageViewIconDayHome.setBackgroundResource(com.example.weatherforecastapplication.R.drawable.img_6)   //thunderstorm
            "13d","13n" -> binding.imageViewIconDayHome.setBackgroundResource(com.example.weatherforecastapplication.R.drawable.img_17) //snow
            else->{//mist
                var linkIcon="https://openweathermap.org/img/wn/${iconName}@2x.png"
                Picasso.get().load(linkIcon).into(holder.binding.imageViewIconDayHome)
            }
        }



    }

    override fun getItemCount(): Int =days.size

    fun setData(value: List<Daily>){
        this.days=value as List<Daily>
        notifyDataSetChanged()
    }
    class ViewHolder(var binding: RowDaysHomeBinding): RecyclerView.ViewHolder(binding.root)
}