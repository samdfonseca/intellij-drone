package com.nytm.intellijDrone.droneApi

import java.util.*
import org.apache.commons.lang3.builder.HashCodeBuilder
import org.apache.commons.lang3.builder.EqualsBuilder

data class DroneSecret(
    val event: Array<DroneEvent>,
    val id: Int,
    val image: Optional<String>,
    val name: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DroneSecret

        return EqualsBuilder()
            .append(event, other.event)
            .append(id, other.id)
            .append(image, other.image)
            .append(name, other.name)
            .isEquals
    }

    override fun hashCode(): Int {
        return HashCodeBuilder()
            .append(event)
            .append(id)
            .append(image)
            .append(name)
            .toHashCode()
    }
}
