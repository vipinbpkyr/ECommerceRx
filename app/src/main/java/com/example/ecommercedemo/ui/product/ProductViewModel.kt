package com.example.ecommercedemo.ui.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommercedemo.repository.Repository
import com.example.ecommercedemo.vo.Resource
import com.example.ecommercedemo.vo.test2.ProductResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductViewModel
@Inject constructor(private val repository: Repository): ViewModel() {
    private val disposables = CompositeDisposable()
    val response: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()

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
}