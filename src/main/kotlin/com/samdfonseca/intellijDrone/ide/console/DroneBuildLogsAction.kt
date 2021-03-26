package com.samdfonseca.intellijDrone.ide.console

import com.intellij.execution.ui.ConsoleView
import com.intellij.execution.ui.ConsoleViewContentType
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.project.DumbAwareRunnable
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.content.Content

class DroneBuildLogsAction : DumbAwareAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val windowContent = ToolWindowManager.getInstance(e.project!!)
            .getToolWindow("Drone Logs")
            .contentManager
            .getContent(0)
        val console = (windowContent?.component as SimpleToolWindowPanel).component?.getComponent(0) as ConsoleView
    }
}
