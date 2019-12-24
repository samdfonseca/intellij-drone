package com.samdfonseca.intellijDrone.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.StoragePathMacros
import com.intellij.openapi.components.Storage
import com.intellij.openapi.project.Project
import com.samdfonseca.intellijDrone.logger
import org.jdom.Element
import com.intellij.util.xmlb.XmlSerializer.deserializeInto

@com.intellij.openapi.components.State(name = "DroneProjectSettingsProvider", storages = [
    Storage(StoragePathMacros.WORKSPACE_FILE),
    Storage(value = "drone.xml")
])
class DroneProjectSettingsProvider(val project: Project)
    : PersistentStateComponent<DroneProjectSettingsProvider.State> {
    data class State (
        var repo: String? = null
    )

    private var _data = State()
    var data: State
        get() = this._data
        set(value) {
            this._data = value
        }

    override fun getState(): State? {
        return this.data
    }

    override fun loadState(state: State) {
        this.data = state
    }

    companion object {
        fun create(project: Project) = ServiceManager.getService(project, DroneSettingsProvider::class.java)!!
    }
}

val Project.droneProjectSettingsProvider: DroneProjectSettingsProvider
    get() = this.getComponent(DroneProjectSettingsProvider::class.java)
