package com.example.ecommercedemo.repository

import com.example.ecommercedemo.network.AppInterceptor
import com.example.ecommercedemo.network.AppService
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val appService: AppService) {
    fun fetch(): Single<ResponseBody> = appService.fetch()
    fun fetch3(): Single<ResponseBody> {
        val apiService = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
//            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addCallAdapterFactory(LiveDataCallAdapterFactory())
//            .client(OkHttpClient.Builder().addInterceptor(AppInterceptor()).build())
            .client(OkHttpClient.Builder().addInterceptor(AppInterceptor()).build())
            .build()
            .create(AppService::class.java)
       return apiService.fetch()

    }
    fun fetch2() {

        val apiService = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addCallAdapterFactory(LiveDataCallAdapterFactory())
//            .client(OkHttpClient.Builder().addInterceptor(AppInterceptor()).build())
            .client(OkHttpClient.Builder().addInterceptor(AppInterceptor()).build())
            .build()
            .create(AppService::class.java)

        Timber.d("xxx fetch2")
        apiService.fetch2().enqueue(object: Callback<ResponseBody?> {
            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Timber.d("xxx onFailure")
            }

            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                Timber.d("xxx onResponse ${response.body()}")
            }
        })

    }
}