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
import java.text.SimpleDateFormat
import java.util.*

class AdapterHourlyHome (private var hours: List<Hourly>,var temp:String="C") :
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
        var hourlyTime=current.dt
//        var currenthourlyDay= Date(hourlyTime * 1000)
//        var dateDay= SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(currenthourlyDay)
        var timeDay= SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(hourlyTime * 1000))
        var time24Hour= SimpleDateFormat("HH:mm", Locale.ENGLISH).format(Date(hourlyTime * 1000))
       // holder.binding.txtViewTempPerHour.text=current.temp.toString()+"C"
        holder.binding.txtView24hourDay.text="${timeDay}"
        var iconName=current.weather.get(0).icon
            when(iconName){
                "01d","01n" -> binding.imageViewIconPerHour.setBackgroundResource(com.example.weatherforecastapplication.R.drawable.img)     //clear sky
                "02d","02n" -> binding.imageViewIconPerHour.setBackgroundResource(com.example.weatherforecastapplication.R.drawable.img_9)   //few clouds
                "03d","03n" -> binding.imageViewIconPerHour.setBackgroundResource(com.example.weatherforecastapplication.R.drawable.img_10)  //scattered clouds
                "04d","04n" -> binding.imageViewIconPerHour.setBackgroundResource(com.example.weatherforecastapplication.R.drawable.img_7)   //broken clouds
                "09d","09n" -> binding.imageViewIconPerHour.setBackgroundResource(com.example.weatherforecastapplication.R.drawable.img_16)  //shower rain
                "10d","10n" -> binding.imageViewIconPerHour.setBackgroundResource(com.example.weatherforecastapplication.R.drawable.img_12)  //rain
                "11d","11n" -> binding.imageViewIconPerHour.setBackgroundResource(com.example.weatherforecastapplication.R.drawable.img_6)   //thunderstorm
                "13d","13n" -> binding.imageViewIconPerHour.setBackgroundResource(com.example.weatherforecastapplication.R.drawable.img_17) //snow
                else->{//mist
                    var linkIcon="https://openweathermap.org/img/wn/${iconName}@2x.png"
                    Picasso.get().load(linkIcon).into(holder.binding.imageViewIconPerHour)
                }
            }

        if(temp.equals("F")){
            //°F = (°C × 9/5) + 32
            var convertDataTempF =(current.temp *(9/5)) + 32
            val formattedDouble = String.format("%.2f", convertDataTempF)
            holder.binding.txtViewTempPerHour.text=formattedDouble.toString()+"°F"
        }else if(temp.equals("K")){
            //Kelvin = Celsius + 273.15
            var convertDataTempK =current.temp + 273.15
            val formattedDouble = String.format("%.2f", convertDataTempK)
            holder.binding.txtViewTempPerHour.text="${formattedDouble.toString()}"+"°K"
        }else{
            val formattedDouble = String.format("%.2f", current.temp)
            holder.binding.txtViewTempPerHour.text="${formattedDouble.toString()}°C"
        }

    }

    override fun getItemCount(): Int =hours.size

    fun setData(value: List<Hourly>,temp: String){
        this.hours=value as List<Hourly>
        this.temp=temp
        notifyDataSetChanged()
    }
    class ViewHolder(var binding: RowHourlyHomeBinding): RecyclerView.ViewHolder(binding.root)

}
