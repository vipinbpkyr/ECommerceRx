package com.example.ecommercedemo.vo.test2

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("columns")
	val columns: Columns? = null,

	@field:SerializedName("rows")
	val rows: List<RowsItem?>? = null
)