package com.samdfonseca.intellijDrone.droneApi

import com.google.gson.GsonBuilder
import com.samdfonseca.intellijDrone.DroneSettings
import com.samdfonseca.intellijDrone.DroneSettingsProvider
import com.samdfonseca.intellijDrone.RunnableLambda
import com.samdfonseca.intellijDrone.getLogger
import java.util.concurrent.Future
import java.util.concurrent.FutureTask
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DroneAPI(val settings: DroneSettingsProvider) {
    private val logger = getLogger(this)

    fun getService(): DroneAPIService {
        logger.debug("getting DroneAPIService - server:${this.settings.server}  token:${this.settings.token.substring(0, 8)}...")
        val client = OkHttpClient.Builder()
            .addInterceptor(DroneAccessTokenInterceptor(this.settings.token))
            .build()
        return Retrofit.Builder()
            .baseUrl(this.settings.server)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                .setLenient()
                .create()))
            .client(client)
            .build()
            .create(DroneAPIService::class.java)
    }

    fun hasRequiredSettings(): Boolean {
        return this.settings.server != "" && this.settings.token != ""
    }
}
