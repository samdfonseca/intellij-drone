package com.samdfonseca.intellijDrone.droneApi

import java.util.*

data class DroneLog(
    val out: String,
    val pos: Int,
    val proc: String,
    private val time: Optional<Int>
) {
    fun getTime() = this.time.orElse(0)
}
