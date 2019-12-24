package com.samdfonseca.intellijDrone.settings

import com.intellij.openapi.util.Comparing.equal
import javax.swing.JComponent
import javax.swing.JPanel
import javax.swing.JPasswordField
import javax.swing.JTextField
import javax.swing.JButton
import javax.swing.JLabel
import com.samdfonseca.intellijDrone.safePassword
import com.samdfonseca.intellijDrone.safeText
import com.samdfonseca.intellijDrone.logger
import com.samdfonseca.intellijDrone.droneApi.DroneAPI
import com.samdfonseca.intellijDrone.droneApi.DroneUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DroneSettingsPanel {
    private lateinit var settingsProvider: DroneSettingsProvider
    lateinit var panel1: JPanel
    lateinit var tokenField: JPasswordField
    lateinit var serverField: JTextField
    lateinit var testConnectionButton: JButton
    lateinit var testConnectionResultsLabel: JLabel

    private var state: DroneSettingsProvider.State
        get() {
            return DroneSettingsProvider.State(
                server = this.serverField.safeText(),
                token = this.tokenField.safePassword()
            )
        }
        set(value) {
            this.logger().debug("setting state $value")
            this.tokenField.text = value.token
            this.serverField.text = value.server
        }

    val modified: Boolean
        get() {
            if (!::settingsProvider.isInitialized) { return false }
            return !equal(this.state, this.settingsProvider.data)
        }

    init {
        this.logger().debug("init-ing")

        this.testConnectionResultsLabel.text = ""
        this.testConnectionButton.addActionListener {
            val settings = DroneSettingsProvider()
            settings.data = DroneSettingsProvider.State(this.serverField.text, this.tokenField.text)
            DroneAPI(settings).getService().currentUserInfo().enqueue(object : Callback<DroneUser> {
                override fun onResponse(call: Call<DroneUser>, response: Response<DroneUser>) {
                    if (response.isSuccessful) {
                        testConnectionResultsLabel.text = "Success!"
                        return
                    }
                    testConnectionResultsLabel.text = "Failed - ${response.code()}"
                }

                override fun onFailure(call: Call<DroneUser>, t: Throwable) {
                    testConnectionResultsLabel.text = t.message
                }
            })
        }
    }

    fun createPanel(settingsProvider: DroneSettingsProvider): JComponent {
        this.logger().debug("creating panel with settings provider", settingsProvider.data.toString())
        this.settingsProvider = settingsProvider
        return this.panel1
    }

    fun apply() {
        this.logger().debug("applying state data", this.state.toString(), this.settingsProvider.data.toString())
        if (!::settingsProvider.isInitialized) {
            this.logger().debug("settingsProvider not initialized")
            return
        }
        this.logger().debug("settingsProvider initialized")
        this.settingsProvider.data = this.state
    }

    fun reset() {
        this.logger().debug("resetting state data", this.state.toString(), this.settingsProvider.data.toString())
        if (!::settingsProvider.isInitialized) {
            this.logger().debug("settingsProvider not initialized")
            return
        }
        this.logger().debug("settingsProvider initialized")
        this.state = this.settingsProvider.data
    }
}
