package com.nytm.intellijDrone.droneApi

import okhttp3.Interceptor
import okhttp3.Response

class DroneAccessTokenInterceptor(val token: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().newBuilder()
            .addQueryParameter("access_token", this.token)
            .build()
        val newRequest = request.newBuilder()
            .url(url)
            .build()
        return chain.proceed(newRequest)
    }
}
