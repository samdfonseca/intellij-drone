package com.nytm.intellijDrone.droneApi

import java.util.*

data class DroneProc(
    val build_id: Int,
    val children: Optional<Array<DroneProc>>,
    val end_time: Int,
    val exit_code: Int,
    val id: Int,
    val machine: String,
    val name: String,
    val pgid: Int,
    val pid: Int,
    val ppid: Int,
    val start_time: Int,
    val state: String
)
