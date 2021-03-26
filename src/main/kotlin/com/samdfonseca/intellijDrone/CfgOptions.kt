package com.samdfonseca.intellijDrone

import com.intellij.util.SmartList

class CfgOptions(
    val keyValueOptions: Map<String, List<String>>,
    val nameOptions: Set<String>
) {
    fun isNameEnabled(name: String): Boolean = name in nameOptions
    fun isNameValueEnabled(name: String, value: String): Boolean = keyValueOptions[name]?.contains(value) ?: false

    companion object {
        fun parse(rawCfgOptions: List<String>): CfgOptions {
            val knownKeyValueOptions = hashMapOf<String, SmartList<String>>()
            val knownNameOptions = hashSetOf<String>()

            for (option in rawCfgOptions) {
                val parts = option.split('=', limit = 2)
                val key = parts.getOrNull(0)
                val value = parts.getOrNull(1)?.trim('"')
                if (key != null && value != null) {
                    knownKeyValueOptions.getOrPut(key, { SmartList() }).add(value)
                } else if (key != null) {
                    knownNameOptions.add(key)
                }
            }
            return CfgOptions(knownKeyValueOptions, knownNameOptions)
        }
    }
}
