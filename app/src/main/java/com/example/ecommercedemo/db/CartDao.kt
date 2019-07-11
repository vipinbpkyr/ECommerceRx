package com.example.ecommercedemo.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommercedemo.vo.Cart
import io.reactivex.Flowable
import androidx.room.Update
import androidx.room.Delete
import io.reactivex.Completable

@Dao
abstract class CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg products: Cart) : Completable

    @Update
    abstract fun updateCart(vararg carts: Cart) : Completable

    @Query("SELECT * FROM Cart")
    abstract fun getCartItems(): Flowable<List<Cart>>

    @Query("SELECT COUNT(*) from Cart")
    abstract fun countCartItem(): Int

    @Query("DELETE FROM Cart")
    abstract fun emptyCart()

    @Delete
    abstract fun deleteCartItem(cart: Cart)
}