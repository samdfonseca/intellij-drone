package com.samdfonseca.intellijDrone.run

import com.intellij.execution.Executor
import com.intellij.execution.configuration.EnvironmentVariablesData
import com.intellij.execution.configurations.*
import com.intellij.execution.process.ProcessAdapter
import com.intellij.execution.process.ProcessEvent
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.options.SettingsEditor
import com.intellij.openapi.project.Project

class DroneRunConfiguration(project: Project, name: String, factory: ConfigurationFactory) : LocatableConfigurationBase(project, factory, name), RunConfigurationWithSuppressedDefaultDebugAction {
    var runTarget: String = "default"
    var runArguments: String = ""
    var runEnvironment: EnvironmentVariablesData = EnvironmentVariablesData.DEFAULT

    val runCommandLine: GeneralCommandLine
        get() {
            val parameters = mutableListOf("run")
            if (runTarget)
        }
}
