package com.project.androidtemplate.data.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val previousRequest = chain.request()
        val request = previousRequest.newBuilder()
            .url(previousRequest.url.toString() + "&apiKey=99c034e8")
            .build()
        Log.e("TAG", "intercept: ${request.url}")
        return chain.proceed(request)
    }
}