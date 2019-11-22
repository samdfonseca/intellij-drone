package com.samdfonseca.intellijDrone

import com.google.gson.GsonBuilder
import retrofit2.Call
import javax.swing.JPasswordField
import javax.swing.JTextField
import retrofit2.Callback
import java.time.Instant

val safePassword: JPasswordField.() -> String = {
    try {
        (this.password ?: CharArray(0)).joinToString("")
    } catch (e: NullPointerException) {
        ""
    }
}

val safeText: JTextField.() -> String = {
    try {
        this.text
    } catch (e: NullPointerException) {
        ""
    }
}

fun String?.loggableSecret(): String {
    val len = this?.length ?: 0
    val hidden = if (len > 4) len - (len / 4) else len
    return this?.substring(hidden)?.padStart(len, '*') ?: ""
}

fun String?.hideSecrets(vararg secrets: String?): String = arrayOf(this ?: "", *secrets)
    .filterNotNull()
    .filterNot { it == "" }
    .reduce { acc: String, s: String -> acc.replace(s, s.loggableSecret()) }

fun Any?.logger(): com.intellij.openapi.diagnostic.Logger {
    if (this == null) {
        return com.intellij.openapi.diagnostic.Log4jBasedLogger.getInstance("<null>")
    }
    return com.intellij.openapi.diagnostic.Log4jBasedLogger.getInstance(this::class.java.name)
}

fun getLogger(instance: Any): org.apache.logging.log4j.Logger {
    return org.apache.logging.log4j.LogManager.getLogger(instance::class.java)
}

interface Logger {
    val logger: org.apache.logging.log4j.Logger
        get() = getLogger(this)
}

fun tsIntToInstant(ts: Int): Instant {
    return Instant.ofEpochSecond(ts.toLong())
}

class RunnableLambda(val lambda: () -> Unit): Runnable {
    override fun run() {
        this.lambda()
    }
}

class JSON<T> {
    fun stringify(obj: Any?) = GsonBuilder().setLenient().create().toJson(obj)
    fun parse(str: String, clazz: Class<T>) = GsonBuilder().setLenient().create().fromJson<T>(str, clazz)
}
