//package com.example.weatherforecastapplication.alerts.view
//
//import android.content.ContentValues
//import android.content.Context
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.weatherforecastapplication.databinding.RowAlertListBinding
//import com.example.weatherforecastapplication.model.CityAlarmList
//import com.squareup.picasso.Picasso
//import java.lang.String
//
//class AdapterAlterList(private var products: List<CityAlarmList>, var onProductClickLesener: OnAlertListener) :
//    RecyclerView.Adapter<AdapterAlterList.ViewHolder>() {
//
//    lateinit var binding:RowAlertListBinding
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val inflater: LayoutInflater =parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        binding = RowAlertListBinding.inflate(inflater, parent, false)
//        Log.i(ContentValues.TAG, "onCreateViewHolder: ")
//        return ViewHolder(binding)
//
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val current=products[position]
// //       Picasso.get().load(current.thumbnail).into(holder.binding.imageView)
////        holder.binding.textViewTitle.text=current.title
////        holder.binding.textViewDescription.text=current.description
////        holder.binding.ratingBar.rating=current.rating as Float
////        holder.binding.textViewBrand.text=current.brand
////        holder.binding.textViewPrice.setText(String.valueOf(products.get(position).price))
////        holder.binding.buttonFav.setOnClickListener{ onProductClickLesener.onClick(products.get(position))
//
//        }
//    }
//
//    override fun getItemCount(): Int =products.size
//
//    fun setData(value: List<CityAlarmList>){
//        this.products=value as List<CityAlarmList>
//        notifyDataSetChanged()
//    }
//    class ViewHolder(var binding: RowAlertListBinding): RecyclerView.ViewHolder(binding.root)
//}