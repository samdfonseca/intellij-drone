package com.samdfonseca.intellijDrone.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.diagnostic.Logger
import com.intellij.util.xmlb.annotations.Transient
import com.samdfonseca.intellijDrone.loggableSecret
import com.samdfonseca.intellijDrone.hideSecrets


@State(name = "DroneSettingsProvider")
class DroneSettingsProvider: PersistentStateComponent<DroneSettingsProvider.State> {
    data class State (
        var server: String? = null,
        @Transient var token: String? = null
    )

    private var _data = State()
    var data: State
        get() {
            return State(
                server = this._data.server,
                token = StoredSecrets().token
            )
        }
        set(value) {
            StoredSecrets().token = value.token
            this._data = State(server = value.server)
        }

    override fun getState(): State {
        return this.data
    }

    override fun loadState(state: State) {
        this.data = state
    }

    private val Log = Logger.getInstance(DroneSettingsProvider::class.java.name)
}
