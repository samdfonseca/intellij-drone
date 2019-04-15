package com.nytm.intellijDrone

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Condition
import java.io.File

class RepoTreeToolWindowCondition: Condition<Project> {
    private val droneYamlFileNames = listOf(".drone.yml", ".drone.yaml")
    override fun value(project: Project?) = this.droneYamlFileNames.any { File(project?.basePath, it).exists() }
}
