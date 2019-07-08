package com.example.ecommercedemo.ui.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommercedemo.repository.Repository
import com.example.ecommercedemo.vo.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductViewModel
@Inject constructor(private val repository: Repository): ViewModel() {
    private val disposables = CompositeDisposable()
    val response: MutableLiveData<Resource<String>> = MutableLiveData()

    override fun onCleared() {
        disposables.clear()
    }

    fun fetchData2(){
        repository.fetch2()
    }
    fun fetchData() {
        disposables.add(repository.fetch()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { response.setValue(Resource.loading("Loading")) }
            .subscribe(
                { result -> response.setValue(Resource.success("result "+result)) },
                { throwable -> response.setValue(Resource.error("Error", null)) }
            )
        )
    }
}