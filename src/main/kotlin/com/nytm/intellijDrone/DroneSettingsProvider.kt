package com.nytm.intellijDrone

import com.intellij.credentialStore.PasswordSafeSettings
import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.generateServiceName
import com.intellij.ide.passwordSafe.impl.PasswordSafeImpl
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.openapi.components.StoragePathMacros
import com.intellij.openapi.project.Project
import com.intellij.util.xmlb.annotations.Transient


@State(name = "DroneSettingsProvider", storages = [Storage(file = StoragePathMacros.WORKSPACE_FILE)])
class DroneSettingsProvider: PersistentStateComponent<DroneSettingsProvider> {
    private val tokenSafeSettings = PasswordSafeSettings()
    private val tokenCredentialAttributes = CredentialAttributes(generateServiceName("Drone", "token"))
    var token: String
        @Transient get() = PasswordSafeImpl(tokenSafeSettings).getPassword(tokenCredentialAttributes) ?: ""
        set(value) = PasswordSafeImpl(this.tokenSafeSettings)
            .setPassword(this.tokenCredentialAttributes, value)

    var server = ""
    var repo = ""

    companion object {
        fun create(project: Project) = ServiceManager.getService(project, DroneSettingsProvider::class.java)!!
    }

    override fun getState(): DroneSettingsProvider? {
        return this
    }

    override fun loadState(state: DroneSettingsProvider) {
        this.server = state.server
        this.token = state.token
        this.repo = state.repo
    }
}
