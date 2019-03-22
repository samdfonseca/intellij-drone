package com.nytm.intellijDrone.droneApi

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call

interface DroneAPIService {
    @GET("/api/user")
    fun currentUserInfo(): Call<DroneUser>

    @GET("/api/user/repos")
    fun currentUserReposList(): Call<Array<DroneRepo>>

    @GET("/api/repos/{owner}/{name}")
    fun repoInfo(@Path("owner") owner: String, @Path("name") name: String): Call<DroneRepo>

    @GET("/api/repos/{owner}/{name}/builds")
    fun repoBuildsList(@Path("owner") owner: String, @Path("name") name: String): Call<Array<DroneBuild>>

    @GET("/api/repos/{owner}/{name}/builds/{buildNumber}")
    fun repoBuildInfo(@Path("owner") owner: String, @Path("name") name: String, @Path("buildNumber") buildNumber: Int): Call<DroneBuild>

    @GET("/api/repos/{owner}/{name}/secrets")
    fun repoSecretsList(@Path("owner") owner: String, @Path("name") name: String): Call<Array<DroneSecret>>

    @GET("/api/repos/{owner}/{name}/secrets/{secretName}")
    fun repoSecretsList(@Path("owner") owner: String, @Path("name") name: String, @Path("secretName") secretName: String): Call<DroneRepo>
}
