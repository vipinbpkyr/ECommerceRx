package com.example.ecommercedemo.vo.test2

import com.google.gson.annotations.SerializedName

data class Columns(

	@field:SerializedName("symbol1")
	val symbol1: List<String?>? = null,

	@field:SerializedName("symbol3")
	val symbol3: List<String?>? = null,

	@field:SerializedName("symbol2")
	val symbol2: List<String?>? = null
)