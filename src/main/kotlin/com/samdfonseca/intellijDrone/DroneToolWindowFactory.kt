package com.samdfonseca.intellijDrone

import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.filters.TextConsoleBuilderImpl
import com.intellij.execution.filters.TextConsoleBuilderFactoryImpl
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory

class DroneToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val settings = DroneSettingsProvider.create(project)
        val textConsoleBuilder = TextConsoleBuilderFactoryImpl.getInstance().createBuilder(project) as TextConsoleBuilderImpl
        val repoTreeToolWindow = DroneToolWindow(toolWindow, settings, textConsoleBuilder)
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(repoTreeToolWindow.getContent(), "", false)
        toolWindow.contentManager.addContent(content)
    }
}
