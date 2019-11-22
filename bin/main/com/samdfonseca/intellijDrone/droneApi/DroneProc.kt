package com.samdfonseca.intellijDrone.droneApi

import java.util.*
import org.apache.commons.lang3.builder.EqualsBuilder
import org.apache.commons.lang3.builder.HashCodeBuilder

data class DroneProc(
    val build_id: Int,
    val children: Array<DroneProc>?,
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
) {
    fun toDisplayString(includePid: Boolean = true) = if (includePid) "${this.pid} - ${this.name} (${this.state})"
                                                      else "${this.name} (${this.state})"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DroneProc

        if (children != null) {
            if (other.children == null) return false
            if (!children.contentEquals(other.children)) return false
        } else if (other.children != null) return false

        return EqualsBuilder()
            .append(build_id, other.build_id)
            .append(end_time, other.end_time)
            .append(exit_code, other.exit_code)
            .append(id, other.id)
            .append(machine, other.machine)
            .append(name, other.name)
            .append(pgid, other.pgid)
            .append(pid, other.pid)
            .append(ppid, other.ppid)
            .append(start_time, other.start_time)
            .append(state, other.state)
            .isEquals
    }

    override fun hashCode(): Int {
        return HashCodeBuilder()
            .append(build_id)
            .append(children)
            .append(end_time)
            .append(exit_code)
            .append(id)
            .append(machine)
            .append(name)
            .append(pgid)
            .append(pid)
            .append(ppid)
            .append(start_time)
            .append(state)
            .toHashCode()
    }
}
