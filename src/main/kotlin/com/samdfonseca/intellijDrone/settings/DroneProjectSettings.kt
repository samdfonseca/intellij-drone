package com.samdfonseca.intellijDrone.settings

import com.intellij.openapi.options.SearchableConfigurable
import javax.swing.JComponent
import com.intellij.openapi.project.Project

class DroneProjectSettings(val project: Project): SearchableConfigurable {
    val panel = DroneProjectSettingsPanel()

    override fun getDisplayName(): String  = "Project"
    override fun getHelpTopic(): String? = "Drone project settings"
    override fun getId(): String = "Drone.Project"
    override fun enableSearch(option: String?): Runnable? = null
    override fun isModified(): Boolean = this.panel.modified
    override fun apply() = this.panel.apply()
    override fun reset() = this.panel.reset()
    override fun disposeUIResources() {}

    override fun createComponent(): JComponent? {
        return this.panel.createPanel(DroneProjectSettingsProvider(this.project))
    }
}
