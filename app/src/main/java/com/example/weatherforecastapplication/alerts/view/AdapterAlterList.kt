package com.example.weatherforecastapplication.alerts.view

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherforecastapplication.databinding.RowAlertListBinding

import com.example.weatherforecastapplication.model.CityAlarmList
import java.text.SimpleDateFormat
import java.util.*

class AdapterAlterList(private var alters: List<CityAlarmList>,var OnClickdeletAlter:OnAlertListener) :
    RecyclerView.Adapter<AdapterAlterList.ViewHolder>() {


    lateinit var binding: RowAlertListBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater =parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = RowAlertListBinding.inflate(inflater, parent, false)
        Log.i(ContentValues.TAG, "onCreateViewHolder: ")

        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val current=alters[position]

        val currentDateStart=current.startDate!!
        // yyyy-MM-dd
        val dateTimeStart = Date(currentDateStart)
        val formattedDateStart = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dateTimeStart)
        val formateTimeStart = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(currentDateStart))

        val currentDateEnd=current.endDate!!
        // yyyy-MM-dd
        val dateTimeEnd = Date(currentDateEnd)
        val formattedDateEnd = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dateTimeEnd)
        val formateTimeEnd = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(currentDateEnd))

        holder.binding.textViewFromDateAlertList.text="From:${formattedDateStart} ${formateTimeEnd}"
         holder.binding.textViewToDateAlertList.text="To:${formattedDateEnd} ${formateTimeEnd}"
        binding.imageViewDeleteAlerts.setOnClickListener {
            OnClickdeletAlter.deleteAlter(current)
        }


    }

    override fun getItemCount(): Int =alters.size

    fun setData(value: List<CityAlarmList>){
        this.alters=value as List<CityAlarmList>
        notifyDataSetChanged()
    }
    class ViewHolder(var binding: RowAlertListBinding): RecyclerView.ViewHolder(binding.root)
}