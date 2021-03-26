package com.samdfonseca.intellijDrone.project.workspace

import com.samdfonseca.intellijDrone.CfgOptions
import java.nio.file.Path

interface DroneWorkspace {
    val manifestPath: Path
    val contentRoot: Path get() = manifestPath.parent
    val workspaceRootPath: Path?
    val cfgOptions: CfgOptions

    interface Step {
        val name: String
        val image: String
        val environment: Map<String, String>?

        interface When {
            enum class Event {
                PUSH, PULL_REQUEST, TAG, DEPLOYMENT
            }
            val event: Event
        }
    }
}
