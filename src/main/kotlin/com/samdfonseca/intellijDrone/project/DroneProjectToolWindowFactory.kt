package com.samdfonseca.intellijDrone.project

import com.intellij.execution.ui.ConsoleView
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.content.ContentFactory

class DroneProjectToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val toolWindowOutputPanel = DroneProjectToolWindowOutputPanel(project)
        val outputTab = ContentFactory.SERVICE.getInstance().createContent(toolWindowOutputPanel, "Output", false)
        toolWindow.contentManager.addContent(outputTab)
        toolWindow.contentManager.setSelectedContent(outputTab)
    }
}

val Project.droneProjectToolWindow: ToolWindow
    get() = ToolWindowManager.getInstance(this).getToolWindow("Drone.Project")

val Project.droneProjectOutputPanel: DroneProjectToolWindowOutputPanel
    get() = this.droneProjectToolWindow.contentManager.getContent(0)?.component as DroneProjectToolWindowOutputPanel

val Project.droneConsoleView: ConsoleView
    get() = this.droneProjectOutputPanel.consoleView
