package com.example.ecommercedemo.vo.test2

import com.google.gson.annotations.SerializedName

data class RowsItem(

	@field:SerializedName("symbol1")
	val symbol1: String? = null,

	@field:SerializedName("symbol3")
	val symbol3: String? = null,

	@field:SerializedName("symbol2")
	val symbol2: String? = null
)