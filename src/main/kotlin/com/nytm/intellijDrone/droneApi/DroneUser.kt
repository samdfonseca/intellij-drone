package com.nytm.intellijDrone.droneApi

data class DroneUser(
    val active: Boolean,
    val avatar_url: String,
    val email: String,
    val id: Int,
    val login: String,
    val synced: Int
)
