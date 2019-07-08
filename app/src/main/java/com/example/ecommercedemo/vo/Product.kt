package com.example.ecommercedemo.vo

import androidx.room.Entity

@Entity(
    primaryKeys = ["symbol1"]
)
data class Product (val symbol1: String,
                    val symbol2: String,
                    val symbol3: String)

data class ProductResponse(
    val rows: List<Product>
)