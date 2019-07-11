package com.example.ecommercedemo.vo

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(
    primaryKeys = ["id"]
)
data class Product (@field:SerializedName("id") val id: String,
                    @field:SerializedName("name") val name: String,
                    @field:SerializedName("image") val image: String,
                    @field:SerializedName("description") val description: String)

data class ProductResponse(
    @field:SerializedName("rows")
    val rows: List<Product>
)