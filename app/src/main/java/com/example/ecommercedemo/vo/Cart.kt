package com.example.ecommercedemo.vo

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(
    primaryKeys = ["id"]
)
data class Cart(@field:SerializedName("ID") val id: String,
                @field:SerializedName("Name") val name: String,
                @field:SerializedName("Image") val image: String) {
}