package com.samdfonseca.intellijDrone.settings

interface SecretsStorage {
    fun getSecret(key: String): String?
    fun setSecret(key: String, value: String?)
}
