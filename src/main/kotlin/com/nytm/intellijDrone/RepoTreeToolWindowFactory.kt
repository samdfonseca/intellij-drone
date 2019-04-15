package com.nytm.intellijDrone

import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.ui.ConsoleView
import com.nytm.intellijDrone.droneApi.DroneAPI
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.content.ContentFactory
import javax.swing.SwingUtilities

class RepoTreeToolWindowFactory : ToolWindowFactory {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val settings = DroneSettingsProvider.create(project)
        val textConsoleBuilder = TextConsoleBuilderFactory.getInstance().createBuilder(project)
        val repoTreeToolWindow = DroneRepoTreeToolWindow(toolWindow, settings, textConsoleBuilder)
        val contentFactory = ContentFactory.SERVICE.getInstance()
        val content = contentFactory.createContent(repoTreeToolWindow.getContent(), "", false)
        toolWindow.contentManager.addContent(content)
    }
}
