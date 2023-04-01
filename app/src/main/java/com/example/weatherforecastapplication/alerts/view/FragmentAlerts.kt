package com.example.weatherforecastapplication.alerts.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.productmvvm.db.ConcreteLocalSource
import com.example.productmvvm.model.Repository
import com.example.productmvvm.network.WeatherClient
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.alerts.viewmodel.AlertViewModel
import com.example.weatherforecastapplication.alerts.viewmodel.AlertViewModelFactory
import com.example.weatherforecastapplication.databinding.FragmentAlertListBinding
import com.example.weatherforecastapplication.databinding.FragmentAlertsBinding
import com.example.weatherforecastapplication.databinding.FragmentHomeBinding
import com.example.weatherforecastapplication.model.CityAlarmList
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class FragmentAlerts : DialogFragment() {

    lateinit var binding:FragmentAlertsBinding
    lateinit var allFactory: AlertViewModelFactory
    lateinit var viewModel: AlertViewModel

    // AlertModel
    private  var cityAlarmList: CityAlarmList= CityAlarmList(0,0,0,0,0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentAlertsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allFactory= AlertViewModelFactory(

            Repository.getInstance(
                WeatherClient.getInstance(), ConcreteLocalSource(requireContext())
            ))

        viewModel= ViewModelProvider(this,allFactory).get(AlertViewModel::class.java)
        binding.textViewFromTime.setOnClickListener {

            setAlarm {
                val currentDate=it
                // yyyy-MM-dd
                val dateTime = Date(currentDate )
                val formattedDate =
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dateTime)

                val formateTime = SimpleDateFormat(
                    "hh:mm a",
                    Locale.ENGLISH
                ).format(Date(currentDate ))

                binding.editTextFromTime.text="${formattedDate} ${formateTime}"
                cityAlarmList.startDate=it
                cityAlarmList.startTime=it

                Log.i(TAG, "onViewCreated: $it")
            }

        }
        binding.textViewToTime.setOnClickListener {

            setAlarm {
                val currentDate=it
                // yyyy-MM-dd
                val dateTime = Date(currentDate )
                val formattedDate =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(dateTime)

                val formateTime = SimpleDateFormat(
                "hh:mm a",
                Locale.ENGLISH
                 ).format(Date(currentDate ))

                binding.editTextToTime.text="${formattedDate} ${formateTime}"
                cityAlarmList.endDate=it
                cityAlarmList.endTime=it

                Log.i(TAG, "//////////////////////////////////////endendendend//////////////: $it")
            }
//
        }
        binding.textViewSaveAlarm.setOnClickListener{
            viewModel.insertAlert(cityAlarmList)
            Navigation.findNavController(requireView()).navigate(R.id.action_fragmentAlerts_to_fragmentAlertList)
        }
    }

    private fun setAlarm(callback: (Long) -> Unit) {
        Calendar.getInstance().apply {
            this.set(Calendar.SECOND, 0)
            this.set(Calendar.MILLISECOND, 0)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                0,
                { _, year, month, day ->
                    this.set(Calendar.YEAR, year)
                    this.set(Calendar.MONTH, month)
                    this.set(Calendar.DAY_OF_MONTH, day)
                    TimePickerDialog(
                        requireContext(),
                        0,
                        { _, hour, minute ->
                            this.set(Calendar.HOUR_OF_DAY, hour)
                            this.set(Calendar.MINUTE, minute)
                            callback(this.timeInMillis)
                        },
                        this.get(Calendar.HOUR_OF_DAY),
                        this.get(Calendar.MINUTE),
                        false
                    ).show()
                },

                this.get(Calendar.YEAR),
                this.get(Calendar.MONTH),
                this.get(Calendar.DAY_OF_MONTH)

            )
            datePickerDialog.datePicker.minDate = Calendar.getInstance().timeInMillis
            datePickerDialog.show()
        }
    }


}