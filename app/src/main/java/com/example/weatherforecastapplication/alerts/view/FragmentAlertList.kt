package com.example.weatherforecastapplication.alerts.view


import android.app.*
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.weatherforecastapplication.alerts.viewmodel.AlertViewModel
import com.example.weatherforecastapplication.alerts.viewmodel.AlertViewModelFactory
import com.example.weatherforecastapplication.databinding.FragmentAlertListBinding
import com.example.weatherforecastapplication.model.CityAlarmList
import com.example.weatherforecastapplication.model.Repository
import com.example.weatherforecastapplication.network.ApiState
import com.example.weatherforecastapplication.network.ConcreteLocalSource
import com.example.weatherforecastapplication.network.WeatherClient
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar


class FragmentAlertList : Fragment() ,OnAlertListener {

    lateinit var binding: FragmentAlertListBinding
    lateinit var picker : MaterialTimePicker
    private var calender  = Calendar.getInstance()

    lateinit var allFactory: AlertViewModelFactory
    lateinit var viewModel: AlertViewModel

    lateinit var recyclerAdapterAlertList: AdapterAlterList
    var alterList= mutableListOf<CityAlarmList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // return inflater.inflate(R.layout.fragment_home, container, false)
        binding= FragmentAlertListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allFactory= AlertViewModelFactory(

            Repository.getInstance(
                WeatherClient.getInstance(), ConcreteLocalSource(requireContext())
            ))

        viewModel= ViewModelProvider(this,allFactory).get(AlertViewModel::class.java)


        recyclerAdapterAlertList= AdapterAlterList(alterList,this)
        binding.buttonAddAlertsList.setOnClickListener{
            val dialogFragment = FragmentAlerts()
            dialogFragment.show(requireFragmentManager(), "MyDialogFragment")

        }

        lifecycleScope.launch {
            viewModel.uiState.collectLatest { alert ->when (alert) {

                is ApiState.SuccessAlert -> {
                    //initialization
                    recyclerAdapterAlertList.setData(alert.data)
                    binding.recyclerViewAlertsList.adapter=recyclerAdapterAlertList
                    recyclerAdapterAlertList.notifyDataSetChanged()
                }
                else ->{
                    println("///////////////////////////////////////////////////////////not display")
                    Log.i(ContentValues.TAG, "onViewCreated: //////////////////////////not display")
                }
            }
            }
        }

    }
    fun showTimePicker(){
        picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Alarm time")
                .build()

        fragmentManager?.let { picker.show(it,"SKY") }
        picker.addOnPositiveButtonClickListener{
            if(picker.hour >12){
                val text =String.format("%02d",picker.hour-12)+":"+String.format("%02d",picker.minute)+"PM"
            }else{
                String.format("%02d",picker.hour)+":"+String.format("%02d",picker.minute)+"AM"
            }
            calender = Calendar.getInstance()
            calender[Calendar.HOUR_OF_DAY]=picker.hour
            calender[Calendar.MINUTE] = picker.minute
            calender[Calendar.SECOND] = 0
            calender[Calendar.MILLISECOND] = 0
        }
    }


    override fun deleteAlter(cityAlarmList: CityAlarmList) {
        DealogdeleteItem(cityAlarmList)
    }
    private  fun DealogdeleteItem(cityAlarmList: CityAlarmList) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete item")
        builder.setMessage("Are you sure you want to delete this item?")
        builder.setPositiveButton("Yes") { dialog, which ->
            viewModel.deleteAlert(cityAlarmList)
            recyclerAdapterAlertList.notifyDataSetChanged()
        }
        builder.setNegativeButton("No", null)
        builder.show()
    }


}