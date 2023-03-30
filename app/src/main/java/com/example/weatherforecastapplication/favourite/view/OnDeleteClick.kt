package com.example.weatherforecastapplication.favourite.view

import com.example.weatherforecastapplication.model.Favourite

interface OnDeleteClick {
    fun deleteItem(favourite: Favourite)
    fun setData(favourite: Favourite)
}
