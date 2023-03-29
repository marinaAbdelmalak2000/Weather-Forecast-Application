package com.example.weatherforecastapplication.favourite.view

import android.app.FragmentTransaction
import android.app.PendingIntent.getActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.productmvvm.db.ConcreteLocalSource
import com.example.productmvvm.model.Repository
import com.example.productmvvm.network.WeatherClient
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.WeatherViewModel
import com.example.weatherforecastapplication.WeatherViewModelFactory
import com.example.weatherforecastapplication.databinding.FragmentFavouriteListBinding
import com.example.weatherforecastapplication.databinding.FragmentHomeBinding
import com.example.weatherforecastapplication.favourite.viewmodel.FavouriteViewModel
import com.example.weatherforecastapplication.favourite.viewmodel.FavouriteViewModelFactory
import com.example.weatherforecastapplication.map.FragmentMap
import com.example.weatherforecastapplication.model.Favourite

class FragmentFavouriteList : Fragment() {

    lateinit var binding: FragmentFavouriteListBinding

    lateinit var allFactory: FavouriteViewModelFactory
    lateinit var viewModel: FavouriteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentFavouriteListBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allFactory= FavouriteViewModelFactory(

            Repository.getInstance(
                WeatherClient.getInstance(), ConcreteLocalSource(requireContext())
            ))

        viewModel= ViewModelProvider(this,allFactory).get(FavouriteViewModel::class.java)


        binding.buttonSAVEFavouriteList.visibility=View.GONE
        binding.buttonAddFavouriteList.setOnClickListener{
            changeFragment(FragmentMap())

        }
        binding.buttonSAVEFavouriteList.setOnClickListener{
            changeFragment(FragmentFavouriteList())
            val city=viewModel.cityName
            if(city!=null){
                viewModel.insertFavourite(Favourite(CityName = city))
            }
            binding.buttonAddFavouriteList.visibility = View.VISIBLE
            binding.buttonSAVEFavouriteList.visibility=View.GONE
        }
    }

    fun changeFragment(fragmentSelect:Fragment) {
        val fragment = requireActivity().supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_contanerFavouriteList, fragmentSelect).commit()
        binding.buttonAddFavouriteList.visibility = View.GONE
        binding.buttonSAVEFavouriteList.visibility=View.VISIBLE

    }
}


