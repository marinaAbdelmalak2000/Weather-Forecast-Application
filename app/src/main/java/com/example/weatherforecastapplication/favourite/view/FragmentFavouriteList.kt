package com.example.weatherforecastapplication.favourite.view

import android.app.AlertDialog
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
import com.example.weatherforecastapplication.databinding.FragmentFavouriteListBinding
import com.example.weatherforecastapplication.favourite.viewmodel.FavouriteViewModel
import com.example.weatherforecastapplication.favourite.viewmodel.FavouriteViewModelFactory
import androidx.navigation.Navigation.findNavController
import com.example.weatherforecastapplication.R
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

        binding.buttonAddFavouriteList.setOnClickListener{
             findNavController(requireView()).navigate(R.id.action_fragmentFavouriteList_to_fragmentMap2)

        }

      //  sharedLocationMap= requireActivity().getSharedPreferences("LastLocationMap", Context.MODE_PRIVATE)

        recyclerAdapterFavouriteList= AdapterFavouriteList(favouriteList,this)

        lifecycleScope.launch {
            viewModel.favourite.collectLatest { favouriteCity ->when (favouriteCity) {

                is ApiState.SuccessFavourite -> {

                  viewModel.getLocalFavourite()
                    //initialization
                    recyclerAdapterFavouriteList.setData(favouriteCity.data)
                    binding.recyclerViewFavouriteList.adapter=recyclerAdapterFavouriteList
                    recyclerAdapterFavouriteList.notifyDataSetChanged()
                }
                else ->{
                    println("///////////////////////////////////////////////////////////not display")
                    Log.i(TAG, "onViewCreated: //////////////////////////not display")
                   // binding.fragmentContanerFavouriteList.visibility=View.VISIBLE
                }
                }
            }
        }

    }


    override fun deleteItem(favourite: Favourite) {
        Log.i(TAG, "deleteItem: //////////// ${favourite}")
        DealogdeleteItem(favourite)
    }

    override fun setData(favourite: Favourite) {
        findNavController(requireView()).navigate(R.id.action_fragmentFavouriteList_to_fragmentFavourite)
    }

   private  fun DealogdeleteItem(favourite: Favourite) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete item")
        builder.setMessage("Are you sure you want to delete this item?")
        builder.setPositiveButton("Yes") { dialog, which ->
            viewModel.deleteFavourite(favourite)
            recyclerAdapterFavouriteList.notifyDataSetChanged()
        }
        builder.setNegativeButton("No", null)
        builder.show()
    }



}


