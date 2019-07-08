package com.example.ecommercedemo.repository

import com.example.ecommercedemo.network.AppService
import io.reactivex.Single
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val appService: AppService) {
    fun fetch(): Single<ResponseBody> = appService.fetch()
}