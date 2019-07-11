package com.example.ecommercedemo.network

import com.example.ecommercedemo.vo.ProductResponse
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET


interface AppService {
    @GET("http://gsx2json.com/api?id=1Vwwhjh9pvQ-Qn1WwGFeNlbo0a0Xy9JklKek3V8977Ug&sheet=3")
    fun fetch(): Observable<ProductResponse>
    @GET("https://www.google.co.in/")
    fun fetch2(): Call<ResponseBody>
}