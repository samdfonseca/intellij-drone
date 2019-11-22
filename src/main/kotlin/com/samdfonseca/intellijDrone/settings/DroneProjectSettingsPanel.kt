package com.samdfonseca.intellijDrone.settings

import com.intellij.openapi.util.Comparing.equal
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JList
import javax.swing.JTextField
import com.samdfonseca.intellijDrone.safePassword
import com.samdfonseca.intellijDrone.safeText
import com.samdfonseca.intellijDrone.logger

class DroneProjectSettingsPanel {
    private lateinit var settingsProvider: DroneProjectSettingsProvider
    lateinit var panel1: JPanel
    lateinit var reposList: JList<String>

    private var state: DroneProjectSettingsProvider.State
        get() = DroneProjectSettingsProvider.State(
            repo = this.reposList.selectedValue
        )
        set(value) {
            this.reposList.setSelectedValue(value.repo, true)
        }

    val modified: Boolean
        get() {
            if (!::settingsProvider.isInitialized) { return false }
            return !equal(this.state, this.settingsProvider.data)
        }

    fun createPanel(settingsProvider: DroneProjectSettingsProvider): JComponent {
        this.settingsProvider = settingsProvider
        return this.panel1
    }

    fun apply() {
        this.logger().debug("applying state data", this.state.toString(), this.settingsProvider.data.toString())
        if (!::settingsProvider.isInitialized) { return }
        this.settingsProvider.data = this.state
    }

    fun reset() {
        this.logger().debug("resetting state data", this.state.toString(), this.settingsProvider.data.toString())
        if (!::settingsProvider.isInitialized) { return }
        this.state = this.settingsProvider.data
    }
}
