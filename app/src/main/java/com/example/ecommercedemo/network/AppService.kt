package com.example.ecommercedemo.network

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET


interface AppService {
    @GET("https://www.johnjacobseyewear.com/apps/johnjacobseyewear/interfaces/interfacePincode.php?pincode=682042")
    fun fetch(): Single<ResponseBody>
    @GET("https://www.johnjacobseyewear.com/apps/johnjacobseyewear/interfaces/interfacePincode.php?pincode=682042")
    fun fetch2(): Call<ResponseBody>
}