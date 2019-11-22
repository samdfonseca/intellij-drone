package com.samdfonseca.intellijDrone.droneApi


data class DroneLog(
    val out: String,
    val pos: Int,
    val proc: String,
    val time: Int?
) {
    fun isInput() = this.out.startsWith("+ ")
    fun isOutput() = !this.isInput()
}
