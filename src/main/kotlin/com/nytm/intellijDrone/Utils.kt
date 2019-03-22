package com.nytm.intellijDrone

import retrofit2.Call
import javax.swing.JPasswordField
import javax.swing.JTextField
import retrofit2.Callback
import java.time.Instant


val safePassword: JPasswordField.() -> CharArray = {
    try {
        this.password
    } catch (e: NullPointerException) {
        CharArray(0)
    }
}

val safeText: JTextField.() -> String = {
    try {
        this.text
    } catch (e: NullPointerException) {
        ""
    }
}

fun getLogger(instance: Any): org.apache.logging.log4j.Logger {
    return org.apache.logging.log4j.LogManager.getLogger(instance::class.java)
}

fun tsIntToInstant(ts: Int): Instant {
    return Instant.ofEpochSecond(ts.toLong())
}
