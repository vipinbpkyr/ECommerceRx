package com.example.ecommercedemo.ui.product

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommercedemo.repository.Repository
import com.example.ecommercedemo.vo.Cart
import com.example.ecommercedemo.vo.Product
import com.example.ecommercedemo.vo.ProductResponse
import com.example.ecommercedemo.vo.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class ProductViewModel
@Inject constructor(private val repository: Repository, private val disposables: CompositeDisposable): ViewModel() {
    private lateinit var mSelectedProduct: Product
    //    private val disposables = CompositeDisposable()
    val response: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()
    val cartCount: MutableLiveData<Int> = MutableLiveData()
    private val viewModelJob = Job()

    override fun onCleared() {
        // Using clear will clear all, but can accept new disposable
        disposables.clear()
        viewModelJob.cancel()

        // Using dispose will clear all and set isDisposed = true, so it will not accept any new disposable
//        disposables.dispose()
    }

    fun fetchData() {
        disposables.add(repository.fetch()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Resource.loading(null)) }
//            .map { result -> "map $result" }
            .subscribe(
                { result -> response.setValue(Resource.success(result)) },
                { throwable -> response.setValue(Resource.error("Error $throwable", null)) }
            )
        )
    }

    fun addToCart() {
        disposables.add(repository.insert(Cart(mSelectedProduct.id, mSelectedProduct.name, mSelectedProduct.image))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Timber.d("xxx addToCart success")},
                { error -> Timber.d("xxx addToCart failed $error") }))
    }

    fun removeCart() {
        disposables.add(repository.emptyCartById(mSelectedProduct.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ Timber.d("xxx removeCart success")},
                { error -> Timber.d("xxx removeCart failed $error") }))
    }

    fun observeCartCount() : LiveData<Int> = repository.countCartItem()

    fun observeRxCartCount() = disposables.add(repository.countCartItemRx()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ cartCount.value = it},
            { error -> Timber.d("xxx cart count failed $error") }))

    fun observeCartCountById() : LiveData<Int> = repository.countCartItemById(mSelectedProduct.id)

    fun setSelected(product: Product) {
        mSelectedProduct = product
    }

     fun downLoadMultipleImages() {
        // launch a coroutine in viewModelScope
        viewModelScope.launch(Dispatchers.IO) {
            // slowFetch()
            var list: MutableList<Bitmap>
            var urls = listOf<String>("http://p.imgci.com/db/PICTURES/CMS/128400/128483.1.jpg","http://p.imgci.com/db/PICTURES/CMS/289000/289002.1.jpg")
            list = repository.getBitMaps(urls)
            Timber.e("downLoadMultipleImages ${list.size} $list ")
        }
    }

}