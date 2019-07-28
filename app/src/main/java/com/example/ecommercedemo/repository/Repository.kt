package com.example.ecommercedemo.repository

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import com.example.ecommercedemo.db.CartDao
import com.example.ecommercedemo.network.AppService
import com.example.ecommercedemo.vo.Cart
import com.example.ecommercedemo.vo.ProductResponse
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton
import android.graphics.BitmapFactory
import timber.log.Timber
import java.net.HttpURLConnection
import java.net.URL


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

     fun getBitMaps(urls: List<String>): MutableList<Bitmap> {
        var list = mutableListOf<Bitmap>()
        urls.forEach { list.add(getBitmapFromUrl1(it)) }

        return list
    }

     private fun getBitmapFromUrl1(imageUrl: String): Bitmap {
        Timber.e("getBitmapFromUrl1 $imageUrl")
        val url = URL(imageUrl)
        val connection = url.openConnection() as HttpURLConnection
        val `is` = connection.inputStream
        return BitmapFactory.decodeStream(`is`)
    }
}