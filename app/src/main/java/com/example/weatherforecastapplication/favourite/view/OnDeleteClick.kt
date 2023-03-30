package com.example.weatherforecastapplication.favourite.view

import android.view.View
import com.example.weatherforecastapplication.model.Favourite

interface OnDeleteClick {
    fun deleteItem(favourite: Favourite)
}
