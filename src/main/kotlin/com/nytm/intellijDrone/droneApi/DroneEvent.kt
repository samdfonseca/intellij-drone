package com.nytm.intellijDrone.droneApi

import com.google.gson.annotations.SerializedName

enum class DroneEvent {
    @SerializedName("deployment") DEPLOYMENT,
    @SerializedName("pull_request") PULL_REQUEST,
    @SerializedName("push") PUSH,
    @SerializedName("tag") TAG
}
