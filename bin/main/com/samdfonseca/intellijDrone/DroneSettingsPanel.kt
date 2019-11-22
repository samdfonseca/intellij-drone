package com.samdfonseca.intellijDrone

import com.intellij.openapi.util.Comparing.equal
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JPasswordField
import javax.swing.JTextField


class DroneSettingsPanel {
    private val logger = getLogger(this)
    lateinit var panel1: JPanel
    lateinit var settingsProvider: DroneSettingsProvider

    lateinit var tokenField: JPasswordField
    private var token: String
        get() = this.tokenField.safePassword().joinToString("")
        set(value) {
            this.tokenField.text = value
        }

    lateinit var serverField: JTextField
    private var server: String
        get() = this.serverField.safeText()
        set(value) {
            this.serverField.text = value
        }

    val modified: Boolean
        get() {
            if (!::settingsProvider.isInitialized) { return false }
            return !equal(this.token, this.settingsProvider.token) ||
                   !equal(this.server, this.settingsProvider.server)
        }

    fun createPanel(settingsProvider: DroneSettingsProvider): JComponent {
        this.settingsProvider = settingsProvider
        return this.panel1
    }

    fun apply() {
        if (!::settingsProvider.isInitialized) { return }
        this.settingsProvider.token = this.token
        this.settingsProvider.server = this.server
    }

    fun reset() {
        if (!::settingsProvider.isInitialized) { return }
        this.token = this.settingsProvider.token
        this.server = this.settingsProvider.server
    }
}
