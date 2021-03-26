package com.samdfonseca.intellijDrone.project.model

import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.util.messages.Topic
import java.nio.file.Path
import java.util.concurrent.CompletableFuture

interface DroneProjectsService {
    val allProjects: Collection<DroneProject>
    val hasAtLeastOneValidProject: Boolean

    fun findProjectForFile(file: VirtualFile): DroneProject?
    fun attachDroneProject(pipelineConfig: Path): Boolean
    fun detachDroneProject(droneProject: DroneProject)
    fun refreshAllProjects(): CompletableFuture<out List<DroneProject>>
    fun discoverAndRefresh(): CompletableFuture<out List<DroneProject>>

    companion object {
        val DRONE_PROJECTS_TOPIC: Topic<DroneProjectsListener> = Topic(
            "drone projects update",
            DroneProjectsListener::class.java
        )
    }
}

val Project.droneProjects: DroneProjectsService get() = service()

fun guessAndSetupDroneProject(project: Project, explicitRequest: Boolean = false): Boolean {
    if (!explicitRequest) {
        val alreadyTried = run {
            val key = "com.samdfonseca.intellijDrone.project.model.PROJECT_DISCOVERY"
            val properties = PropertiesComponent.getInstance(project)
            val alreadyTried = properties.getBoolean(key)
            properties.setValue(key, true)
            alreadyTried
        }
        if (alreadyTried) return false
    }
    if (!project.droneProjects.hasAtLeastOneValidProject) {
        project.droneProjects.discoverAndRefresh()
        return true
    }
    return false
}
