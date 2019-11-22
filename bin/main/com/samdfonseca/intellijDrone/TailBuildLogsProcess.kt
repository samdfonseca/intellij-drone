package com.samdfonseca.intellijDrone

import com.samdfonseca.intellijDrone.droneApi.DroneAPIService
import com.samdfonseca.intellijDrone.droneApi.DroneBuild
import com.samdfonseca.intellijDrone.droneApi.DroneProc
import com.samdfonseca.intellijDrone.droneApi.DroneRepo
import java.io.*

class TailBuildLogsProcess(val droneAPIService: DroneAPIService,
                           val repo: DroneRepo,
                           val build: DroneBuild,
                           val proc: DroneProc,
                           val logFile: File) {
    fun appendLogs(offset: Int): Int {
        val resp = droneAPIService.repoBuildLogs(repo.owner, repo.name, build.number, proc.pid).execute()
        val logs = resp.body()
        val newLogs = logs?.sliceArray(IntRange(offset, logs.size-1))
        newLogs?.forEach { logFile.appendText(it.out) }
        return if (newLogs == null) offset else offset + newLogs.size
    }
}
