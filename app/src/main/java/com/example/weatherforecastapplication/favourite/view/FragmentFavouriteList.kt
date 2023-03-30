package com.example.weatherforecastapplication.favourite.view

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.productmvvm.db.ConcreteLocalSource
import com.example.productmvvm.model.Repository
import com.example.productmvvm.network.WeatherClient
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.databinding.FragmentFavouriteListBinding
import com.example.weatherforecastapplication.favourite.viewmodel.FavouriteViewModel
import com.example.weatherforecastapplication.favourite.viewmodel.FavouriteViewModelFactory
import com.example.weatherforecastapplication.map.FragmentMap
import com.example.weatherforecastapplication.model.Favourite
import com.example.weatherforecastapplication.network.ApiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FragmentFavouriteList : Fragment() ,OnDeleteClick{

    lateinit var binding: FragmentFavouriteListBinding

    lateinit var allFactory: FavouriteViewModelFactory
    lateinit var viewModel: FavouriteViewModel

    lateinit var sharedLocationMap: SharedPreferences
    lateinit var  cityNameMap:String

    lateinit var recyclerAdapterFavouriteList: AdapterFavouriteList
    var favouriteList= mutableListOf<Favourite>()
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

        recyclerAdapterFavouriteList= AdapterFavouriteList(favouriteList,this)
        //initialization
        binding.recyclerViewFavouriteList.adapter=recyclerAdapterFavouriteList


        binding.buttonSAVEFavouriteList.visibility=View.GONE
        binding.buttonAddFavouriteList.setOnClickListener{
            changeFragment(FragmentMap())
            //  var navController = findNavController(this).navigate(R.id.action_fragmentFavouriteList_to_fragmentMap2)

            // findNavController(this@FragmentFavouriteList).navigate(R.id.action_fragmentFavouriteList_to_fragmentMap2)
            binding.fragmentContanerFavouriteList.visibility=View.VISIBLE
            binding.buttonAddFavouriteList.visibility = View.GONE
            binding.buttonSAVEFavouriteList.visibility=View.VISIBLE
            binding.recyclerViewFavouriteList.visibility=View.GONE

        }

        sharedLocationMap= requireActivity().getSharedPreferences("LastLocationMap", Context.MODE_PRIVATE)


        binding.buttonSAVEFavouriteList.setOnClickListener{

            changeFragment(FragmentFavouriteList())
            val longitudeMap=sharedLocationMap.getString("longitudeMap","30.20")
            val latitudeMap=sharedLocationMap.getString("latitudeMap","30.55")
            cityNameMap= sharedLocationMap.getString("cityNameMap","null").toString()

            viewModel.insertFavourite(Favourite(CityName=cityNameMap,lon=longitudeMap, lan =latitudeMap))
            Log.i(TAG, "onViewCreated: ${longitudeMap}  ${cityNameMap}")

            binding.buttonAddFavouriteList.visibility = View.VISIBLE
            binding.buttonSAVEFavouriteList.visibility=View.GONE
            binding.recyclerViewFavouriteList.visibility=View.VISIBLE
            binding.fragmentContanerFavouriteList.visibility=View.GONE
            //  requireParentFragment().isVisible
        }

        lifecycleScope.launch {

            viewModel.favourite.collectLatest { favouriteCity ->when (favouriteCity) {

                is ApiState.SuccessFavourite -> {
                    binding.fragmentContanerFavouriteList.visibility=View.GONE
                    binding.recyclerViewFavouriteList.visibility=View.VISIBLE
                  viewModel.getLocalFavourite()
                    val favouriteList = favouriteCity.data

                    recyclerAdapterFavouriteList.setData(favouriteList)
                    recyclerAdapterFavouriteList.notifyDataSetChanged()
                   // println("////////////////  ${viewModel.getLocalFavourite()}")
                }
                else ->{
                   // binding.fragmentContanerFavouriteList.visibility=View.VISIBLE

                }
                }
            }
        }
    }

    fun changeFragment(fragmentSelect:Fragment) {
        val fragment = requireActivity().supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_contanerFavouriteList, fragmentSelect).commit()
        binding.fragmentContanerFavouriteList.visibility=View.GONE
        binding.recyclerViewFavouriteList.visibility=View.VISIBLE
    }

    override fun deleteItem(favourite: Favourite) {
        Log.i(TAG, "deleteItem: //////////// ${favourite}")
        viewModel.deleteFavourite(favourite)

    }


//    override fun deleteItem(favourite: Favourite) {
//        val builder = AlertDialog.Builder(context)
//        builder.setTitle("Delete item")
//        builder.setMessage("Are you sure you want to delete this item?")
//        builder.setPositiveButton("Yes") { dialog, which ->
//            viewModel.deleteFavourite(favourite)
//            println("Delete /////// $favourite")
//        }
//        builder.setNegativeButton("No", null)
//        builder.show()
//    }



}


