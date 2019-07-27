package com.samdfonseca.intellijDrone.run

import com.intellij.execution.filters.Filter
import com.intellij.execution.filters.TextConsoleBuilder
import com.intellij.execution.impl.ConsoleViewImpl
import com.intellij.execution.ui.ConsoleView
import com.intellij.openapi.project.Project

import java.util.ArrayList

class DroneTextConsoleBuilder(val project: Project) : TextConsoleBuilder() {
    private val filters = ArrayList<Filter>()
    private var viewer: Boolean? = null

    private fun createConsole() = ConsoleViewImpl(this.project, this.viewer!!)

    override fun addFilter(filter: Filter) {
        filters.add(filter)
    }

    override fun getConsole(): ConsoleView {
        val cv = this.createConsole()
        this.filters.forEach { cv.addMessageFilter(it) }
        return cv
    }

    override fun setViewer(isViewer: Boolean) {
        this.viewer = isViewer
    }
}
