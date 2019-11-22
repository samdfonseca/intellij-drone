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
import org.jdom.Element

class DroneRunConfiguration(project: Project, name: String, factory: ConfigurationFactory)
    : LocatableConfigurationBase<Element>(project, factory, name), RunConfigurationWithSuppressedDefaultDebugAction {
    override fun getState(executor: Executor, environment: ExecutionEnvironment): RunProfileState? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var runTarget: String = "default"
    var runArguments: String = ""
    var runEnvironment: EnvironmentVariablesData = EnvironmentVariablesData.DEFAULT

//    val runCommandLine: GeneralCommandLine
//        get() {
//            val parameters = mutableListOf("run")
//            if (runTarget)
//        }
}
