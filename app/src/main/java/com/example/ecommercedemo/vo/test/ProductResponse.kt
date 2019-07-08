package com.example.ecommercedemo.vo.test

data class ProductResponse(
    val rows: List<Product>
)

data class Product(
    val symbol1: String,
    val symbol2: String,
    val symbol3: String
)