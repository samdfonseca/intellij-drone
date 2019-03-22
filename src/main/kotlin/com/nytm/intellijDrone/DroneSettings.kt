package com.nytm.intellijDrone

import com.intellij.openapi.options.SearchableConfigurable
import javax.swing.JComponent
import com.intellij.openapi.project.Project


class DroneSettings(val project: Project): SearchableConfigurable {
    val panel = DroneSettingsPanel()

    override fun getDisplayName(): String  = "Drone"
    override fun getHelpTopic(): String? = "Drone"
    override fun getId(): String = "Drone"
    override fun enableSearch(option: String?): Runnable? = null
    override fun isModified(): Boolean = this.panel.modified
    override fun apply() = this.panel.apply()
    override fun reset() = this.panel.reset()
    override fun disposeUIResources() {}

    override fun createComponent(): JComponent? {
        val droneSettingsProvider = DroneSettingsProvider.create(this.project)
        return this.panel.createPanel(droneSettingsProvider)
    }
}
