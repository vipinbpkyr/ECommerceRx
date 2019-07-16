package com.example.ecommercedemo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ecommercedemo.vo.Cart
import io.reactivex.Flowable
import androidx.room.Update
import androidx.room.Delete
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(vararg products: Cart) : Completable

    @Update
    abstract fun updateCart(vararg carts: Cart) : Completable

    @Query("SELECT * FROM Cart")
    abstract fun getCartItems(): Flowable<List<Cart>>

    @Query("SELECT COUNT(*) from Cart")
    abstract fun countCartItem(): LiveData<Int>

    @Query("SELECT COUNT(*) from Cart")
    abstract fun countCartItemRx(): Observable<Int>

    @Query("SELECT COUNT(*) from Cart WHERE id = :ids")
    abstract fun countCartItemById(ids: String): LiveData<Int>

    @Query("DELETE FROM Cart")
    abstract fun emptyCart()

    @Query("DELETE FROM Cart WHERE id = :ids")
    abstract fun emptyCartById(ids: String): Completable

    @Delete
    abstract fun deleteCartItem(cart: Cart): Completable
}