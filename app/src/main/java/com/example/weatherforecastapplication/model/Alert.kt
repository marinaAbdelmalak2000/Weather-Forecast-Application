package com.example.weatherforecastapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class Alert(
    val description: String,
    val end: Int,
    val event: String,
    val sender_name: String,
    val start: Int,
    val tags: List<String>
)
