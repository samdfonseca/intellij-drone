package com.samdfonseca.intellijDrone

import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class DroneToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val settings = DroneSettingsProvider.create(project)
        val textConsoleBuilder = TextConsoleBuilderFactory.getInstance().createBuilder(project)
        val repoTreeToolWindow = DroneToolWindow(toolWindow, settings, textConsoleBuilder)
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(repoTreeToolWindow.getContent(), "", false)
        toolWindow.contentManager.addContent(content)
    }
}
