package com.samdfonseca.intellijDrone.settings

import com.intellij.openapi.options.SearchableConfigurable
import javax.swing.JComponent


class DroneSettings(val settingsProvider: DroneSettingsProvider) : SearchableConfigurable {
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
        this.settingsProvider.secretsStorage = StoredSecrets("com.samdfonseca.intellij-drone")
        return this.panel.createPanel(this.settingsProvider)
    }
}
