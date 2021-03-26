package com.samdfonseca.intellijDrone.project.model

interface DroneProjectsListener {
    fun droneProjectsUpdated(projects: Collection<DroneProject>)
}
