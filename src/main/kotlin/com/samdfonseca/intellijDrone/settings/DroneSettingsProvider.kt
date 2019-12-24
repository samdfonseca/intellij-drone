package com.samdfonseca.intellijDrone.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.diagnostic.Logger
import com.intellij.util.xmlb.annotations.Transient
import com.samdfonseca.intellijDrone.loggableSecret
import com.samdfonseca.intellijDrone.hideSecrets
import com.samdfonseca.intellijDrone.nullIfEmpty
import com.samdfonseca.intellijDrone.logger


@State(name = "DroneSettingsProvider", storages = [Storage(value = "drone.xml")])
class DroneSettingsProvider: PersistentStateComponent<DroneSettingsProvider.State> {
    data class State (
        var server: String? = null,
        @Transient var token: String? = null
    )

    lateinit var secretsStorage: SecretsStorage
    private var _data = State()
    var data: State
        get() {
            return State(
                server = this._data.server,
                token = this.secretsStorage.getSecret("token").nullIfEmpty()
            )
        }
        set(value) {
            this.secretsStorage.setSecret("token", value.token)
            this._data.server = value.server
        }

    override fun getState(): State {
        return this.data
    }

    override fun loadState(state: State) {
        this.data = state
    }

    private val Log = Logger.getInstance(DroneSettingsProvider::class.java.name)
}
