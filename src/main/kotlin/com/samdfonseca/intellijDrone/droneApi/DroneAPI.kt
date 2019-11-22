package com.samdfonseca.intellijDrone.droneApi

import com.intellij.openapi.util.text.StringUtil
import com.google.gson.GsonBuilder
import com.samdfonseca.intellijDrone.settings.DroneSettingsProvider
import com.samdfonseca.intellijDrone.getLogger
import okhttp3.OkHttpClient
import okhttp3.Cache
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DroneAPI(val settings: DroneSettingsProvider) {
    private val logger = getLogger(this)

    fun getService(): DroneAPIService {
        logger.debug("getting DroneAPIService - server:${this.settings.state.server}  token:${this.settings.state.token?.substring(0, 8)}...")
        val client = OkHttpClient.Builder()
            .addInterceptor(DroneAccessTokenInterceptor(this.settings.state.token ?: ""))
            .build()
        return Retrofit.Builder()
            .baseUrl(this.settings.state.server ?: "")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
                .setLenient()
                .create()))
            .client(client)
            .build()
            .create(DroneAPIService::class.java)
    }

    fun hasRequiredSettings(): Boolean {
        return this.settings.state.server != "" && this.settings.state.token != ""
    }
}
