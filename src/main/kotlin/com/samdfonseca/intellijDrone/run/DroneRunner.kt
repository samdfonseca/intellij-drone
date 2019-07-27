package com.samdfonseca.intellijDrone.run

import com.intellij.execution.configurations.RunProfile
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.execution.runners.DefaultProgramRunner
import com.intellij.openapi.diagnostic.Logger

class DroneRunner : DefaultProgramRunner() {
    override fun canRun(executorId: String, profile: RunProfile): Boolean {
        return executorId == DefaultRunExecutor.EXECUTOR_ID
    }
}
