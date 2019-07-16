package com.example.ecommercedemo.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ecommercedemo.db.CartDao
import com.example.ecommercedemo.network.AppService
import com.example.ecommercedemo.vo.Cart
import com.example.ecommercedemo.vo.ProductResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val appService: AppService, private val cartDao: CartDao) {
    fun fetch(): Single<ProductResponse> = appService.fetch()
    fun countCartItemById(id: String): LiveData<Int> {
        return cartDao.countCartItemById(id)
    }

    fun insert(cart: Cart): Completable {
        return cartDao.insert(cart)
    }

    fun emptyCartById(id: String): Completable {
        return cartDao.emptyCartById(id)
    }
    fun countCartItemRx(): Observable<Int> {
        return cartDao.countCartItemRx()
    }

//    @WorkerThread
    fun countCartItem(): LiveData<Int> {
        /*var data : MutableLiveData<Int> = MutableLiveData()
        runBlocking {

            delay(5000)
            data.value = 10
            data = async {
                cartDao.countCartItem() }.await()
        }

            GlobalScope.launch(Dispatchers.IO) {

            }
        }

//        data = cartDao.countCartItem()
            data.value = cartDao.countCartItem().value */
            return cartDao.countCartItem()
        }
}