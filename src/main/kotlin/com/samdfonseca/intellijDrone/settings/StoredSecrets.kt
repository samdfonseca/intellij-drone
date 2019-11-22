package com.samdfonseca.intellijDrone.settings

import com.intellij.credentialStore.CredentialAttributes
import com.intellij.credentialStore.Credentials
import com.intellij.ide.passwordSafe.PasswordSafe

class StoredSecrets {
    var token: String?
        get() = PasswordSafe.instance.get(createCredentialAttributes("token"))?.getPasswordAsString()
        set(value) = PasswordSafe.instance.set(createCredentialAttributes("token"), Credentials(null, value))
    private fun createCredentialAttributes(key: String) = CredentialAttributes("com.samdfonseca.intellij-drone: $key")
}
