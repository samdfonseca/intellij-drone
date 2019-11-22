package com.samdfonseca.intellijDrone.settings

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.util.Comparing.equal
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JPasswordField
import javax.swing.JTextField
import com.samdfonseca.intellijDrone.safePassword
import com.samdfonseca.intellijDrone.safeText
import com.samdfonseca.intellijDrone.logger

class DroneSettingsPanel {
    private lateinit var settingsProvider: DroneSettingsProvider
    lateinit var panel1: JPanel
    lateinit var tokenField: JPasswordField
    lateinit var serverField: JTextField

    private var state: DroneSettingsProvider.State
        get() = DroneSettingsProvider.State(
            server = this.serverField.safeText(),
            token = this.tokenField.safePassword()
        )
        set(value) {
            this.tokenField.text = value.token
            this.serverField.text = value.server
        }

    val modified: Boolean
        get() {
            if (!::settingsProvider.isInitialized) { return false }
            return !equal(this.state, this.settingsProvider.data)
        }

    fun createPanel(settingsProvider: DroneSettingsProvider): JComponent {
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
