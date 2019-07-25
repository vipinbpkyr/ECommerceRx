package com.example.ecommercedemo.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommercedemo.repository.Repository
import com.example.ecommercedemo.vo.Cart
import com.example.ecommercedemo.vo.Product
import com.example.ecommercedemo.vo.ProductResponse
import com.example.ecommercedemo.vo.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

class ProductViewModel
@Inject constructor(private val repository: Repository, private val disposables: CompositeDisposable): ViewModel() {
    private lateinit var mSelectedProduct: Product
    //    private val disposables = CompositeDisposable()
    val response: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()
    val cartCount: MutableLiveData<Int> = MutableLiveData()
    override fun onCleared() {
        // Using clear will clear all, but can accept new disposable
        disposables.clear()

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

}