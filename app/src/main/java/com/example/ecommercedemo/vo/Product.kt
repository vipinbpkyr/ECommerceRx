package com.example.ecommercedemo.vo

import androidx.room.Entity

@Entity(
    primaryKeys = ["name"]
)
data class Product (val name : String)