package com.example.ecommercedemo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.ecommercedemo.vo.Product

@Dao
abstract class CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg products: Product)
}