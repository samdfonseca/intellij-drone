package com.samdfonseca.intellijDrone.project.model

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.UserDataHolderEx
import com.intellij.openapi.vfs.VirtualFile
import java.nio.file.Path

interface DroneProject: UserDataHolderEx {
    val project: Project
    val pipelineConfig: Path
    val rootDir: VirtualFile?
    val workspaceRootDir: VirtualFile?
    val presentableName: String
}
