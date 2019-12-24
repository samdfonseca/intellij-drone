package com.samdfonseca.intellijDrone

import com.intellij.execution.filters.TextConsoleBuilderImpl
import com.intellij.execution.filters.TextConsoleBuilderFactoryImpl
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import com.samdfonseca.intellijDrone.settings.DroneSettingsProvider

class DroneToolWindowFactory(val settingsProvider: DroneSettingsProvider) : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val textConsoleBuilder = TextConsoleBuilderFactoryImpl.getInstance().createBuilder(project) as TextConsoleBuilderImpl
        val repoTreeToolWindow = DroneToolWindow(toolWindow, settingsProvider, textConsoleBuilder)
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(repoTreeToolWindow.getContent(), "", false)
        toolWindow.contentManager.addContent(content)
    }
}
