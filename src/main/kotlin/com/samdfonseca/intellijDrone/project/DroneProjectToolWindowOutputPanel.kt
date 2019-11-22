package com.samdfonseca.intellijDrone.project

import com.intellij.execution.filters.TextConsoleBuilderFactory
import com.intellij.execution.ui.ConsoleView
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.ActionToolbar
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.SimpleToolWindowPanel
import com.samdfonseca.intellijDrone.settings.droneProjectSettingsProvider

class DroneProjectToolWindowOutputPanel(val project: Project) : SimpleToolWindowPanel(false) {
    val toolbar: ActionToolbar = run {
        val actionManager = ActionManager.getInstance()
        actionManager.createActionToolbar("Drone Toolbar", actionManager.getAction("Drone.Menu") as DefaultActionGroup, false)
    }

    val consoleView: ConsoleView = run {
        val builder = TextConsoleBuilderFactory.getInstance().createBuilder(project)
        builder.setViewer(true)
        builder.console
    }

    init {
        setToolbar(toolbar.component)
        toolbar.setTargetComponent(this)
        setContent(consoleView.component)
    }

    fun showPanel() {
        val contentManager = project.droneProjectSettingsProvider
    }

    private val Log = Logger.getInstance(DroneProjectToolWindowOutputPanel::class.java.name)
}
