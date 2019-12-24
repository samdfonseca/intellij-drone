package com.samdfonseca.intellijDrone.settings

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.Credentials
import com.intellij.ide.passwordSafe.PasswordSafe

class StoredSecrets(private val credentialAttributesServiceName: String): SecretsStorage {
    override fun getSecret(key: String): String? {
        return PasswordSafe.instance.get(this.createCredentialAttributes(key))?.getPasswordAsString()
    }

    override fun setSecret(key: String, value: String?) {
        PasswordSafe.instance.set(this.createCredentialAttributes(key), Credentials(null, value))
    }
    var token: String?
        get() = this.getSecret("token")
        set(value) = this.setSecret("token", value)
    private fun createCredentialAttributes(key: String) = CredentialAttributes("$credentialAttributesServiceName: $key")
}
