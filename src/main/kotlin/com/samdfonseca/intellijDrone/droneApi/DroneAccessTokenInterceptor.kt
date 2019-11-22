package com.samdfonseca.intellijDrone.droneApi

import okhttp3.Interceptor
import okhttp3.Response

class DroneAccessTokenInterceptor(private val token: String?): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var url = request.url.newBuilder()
        url = if (token != null) url.addQueryParameter("access_token", this.token) else url
        val newRequest = request.newBuilder()
            .url(url.build())
            .build()
        return chain.proceed(newRequest)
    }
}
