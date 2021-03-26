package com.samdfonseca.intellijDrone.project.toolwindow

import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionToolbar
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.Project

class DroneToolWindow(private val project: Project) {
    val toolbar: ActionToolbar = run {
        val actionManager = ActionManager.getInstance()
        actionManager.createActionToolbar("Drone Toolbar", actionManager.getAction("Drone") as DefaultActionGroup, true)
    }
}
