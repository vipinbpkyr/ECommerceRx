package com.example.ecommercedemo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ecommercedemo.vo.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
}