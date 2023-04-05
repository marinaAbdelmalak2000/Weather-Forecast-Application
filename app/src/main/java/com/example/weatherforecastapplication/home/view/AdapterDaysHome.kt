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

class AdapterDaysHome (private var days: List<Daily>,var temp:String="C") :
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

        if(temp.equals("F")){
            //°F = (°C × 9/5) + 32
            var tempMin =(current.temp.min *(9/5)) + 32
            var tempMax =(current.temp.min *(9/5)) + 32
            val formattedDoubleMin = String.format("%.2f", tempMin)
            val formattedDoubleMax = String.format("%.2f", tempMax)
            holder.binding.textViewTempDayHome.text="${formattedDoubleMin}/${formattedDoubleMax} °F"
        }else if(temp.equals("K")){
            //Kelvin = Celsius + 273.15
            var tempMin =current.temp.min + 273.15
            var tempMax=current.temp.max + 273.15
            val formattedDoubleMin = String.format("%.2f", tempMin)
            val formattedDoubleMax = String.format("%.2f", tempMax)
            holder.binding.textViewTempDayHome.text="${formattedDoubleMin}/${formattedDoubleMax} °K"
        }else{
            val formattedDoubleMin = String.format("%.2f", current.temp.min)
            val formattedDoubleMax = String.format("%.2f", current.temp.max)
            holder.binding.textViewTempDayHome.text="${formattedDoubleMin}/${formattedDoubleMax}°C"
        }

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

    fun setData(value: List<Daily>,temp: String){
        this.days=value as List<Daily>
        this.temp=temp
        notifyDataSetChanged()
    }
    class ViewHolder(var binding: RowDaysHomeBinding): RecyclerView.ViewHolder(binding.root)
}