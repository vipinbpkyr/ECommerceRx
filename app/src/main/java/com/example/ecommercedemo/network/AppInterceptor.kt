package com.example.ecommercedemo.network

import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class AppInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        Timber.d("xxx intercept originalRequest $originalRequest")
        val response  = chain.proceed(originalRequest)
        Timber.d("xxx intercept code ${response.code()}  originalRequest $response")

        return response

    }
}