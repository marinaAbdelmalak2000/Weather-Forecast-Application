package com.example.weatherforecastapplication.favourite.view

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.weatherforecastapplication.databinding.FragmentFavouriteListBinding
import com.example.weatherforecastapplication.favourite.viewmodel.FavouriteViewModel
import com.example.weatherforecastapplication.favourite.viewmodel.FavouriteViewModelFactory
import androidx.navigation.Navigation.findNavController
import com.example.weatherforecastapplication.R
import com.example.weatherforecastapplication.model.Favourite
import com.example.weatherforecastapplication.model.Repository
import com.example.weatherforecastapplication.network.ApiState
import com.example.weatherforecastapplication.network.ConcreteLocalSource
import com.example.weatherforecastapplication.network.WeatherClient
import com.example.weatherforecastapplication.utils.NetwarkInternet
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

    val netwarkInternet= NetwarkInternet()
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
            if(netwarkInternet.isNetworkAvailable(context)==true){
                findNavController(requireView()).navigate(R.id.action_fragmentFavouriteList_to_fragmentMap2)
            }
            else{
                Toast.makeText(activity,"Please, open internet",Toast.LENGTH_SHORT).show();
            }

        }

        recyclerAdapterFavouriteList= AdapterFavouriteList(favouriteList,this)

        lifecycleScope.launch {
            viewModel.favourite.collectLatest { favouriteCity ->when (favouriteCity) {

                is ApiState<Any?>.SuccessFavourite -> {
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
        if(netwarkInternet.isNetworkAvailable(context)==true){
             Navigation.findNavController(requireView())
            .navigate(FragmentFavouriteListDirections.actionFragmentFavouriteListToFragmentFavourite().apply {
                latitute = favourite.lan
                longtute = favourite.lon
                Log.i("ArrrrrgsFavv", "$latitute + $longtute")
            })
        }
        else{
            Toast.makeText(activity,"Please, open internet",Toast.LENGTH_SHORT).show()
        }
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


