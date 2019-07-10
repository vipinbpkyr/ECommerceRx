package com.example.ecommercedemo.repository

import com.example.ecommercedemo.network.AppService
import com.example.ecommercedemo.vo.test2.ProductResponse
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val appService: AppService) {
    fun fetch(): Observable<ProductResponse> = appService.fetch()
}