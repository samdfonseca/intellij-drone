package com.nytm.intellijDrone.droneApi

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call

interface DroneAPIService {
    @GET("/api/user")
    fun currentUserInfo(): Call<DroneUser>

    @GET("/api/user/repos")
    fun currentUserReposList(): Call<Array<DroneRepo>>

    @GET("/api/repos/{repoOwner}/{repoName}")
    fun repoInfo(@Path("repoOwner") repoOwner: String, @Path("repoName") repoName: String): Call<DroneRepo>

    @GET("/api/repos/{repoOwner}/{repoName}/builds")
    fun repoBuildsList(@Path("repoOwner") repoOwner: String, @Path("repoName") repoName: String): Call<Array<DroneBuild>>

    @GET("/api/repos/{repoOwner}/{repoName}/builds/{buildNumber}")
    fun repoBuildInfo(@Path("repoOwner") repoOwner: String, @Path("repoName") repoName: String, @Path("buildNumber") buildNumber: Int): Call<DroneBuild>

    @GET("/api/repos/{repoOwner}/{repoName}/secrets")
    fun repoSecretsList(@Path("repoOwner") repoOwner: String, @Path("repoName") repoName: String): Call<Array<DroneSecret>>

    @GET("/api/repos/{repoOwner}/{repoName}/secrets/{secretName}")
    fun repoSecretsList(@Path("repoOwner") repoOwner: String, @Path("repoName") repoName: String, @Path("secretName") secretName: String): Call<DroneRepo>

    @GET("/api/repos/{repoOwner}/{repoName}/logs/{buildNumber}/{pid}")
    fun repoBuildLogs(@Path("repoOwner") repoOwner: String, @Path("repoName") repoName: String, @Path("buildNumber") buildNumber: Int, @Path("pid") pid: Int): Call<Array<DroneLog>>
}
