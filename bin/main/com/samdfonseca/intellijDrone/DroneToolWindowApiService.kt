package com.samdfonseca.intellijDrone

import com.samdfonseca.intellijDrone.droneApi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.swing.DefaultListModel

class DroneToolWindowApiService(val droneToolWindow: DroneToolWindow, val droneAPI: DroneAPI): Logger {
    init {
        logger.debug("init-ing")
    }

    fun setRepos(selectedRepo: DroneRepo?) {
        logger.debug("setting repos")
        if (!this.droneAPI.hasRequiredSettings()) {
            logger.warn("missing required Drone settings")
            return
        }
        val reposCB = this.droneToolWindow.availableReposComboBox
        reposCB.removeAllItems()
        this.droneAPI.getService().currentUserReposList().enqueue(object : Callback<Array<DroneRepo>> {
            override fun onResponse(call: Call<Array<DroneRepo>>, response: Response<Array<DroneRepo>>) {
                logger.debug("handling currentUserReposList response")
                val repos = response.body()
                repos?.filter { it == selectedRepo }?.forEach { repo: DroneRepo -> Unit
                    logger.debug("adding repo ${repo.full_name}")
                    reposCB.addItem(repo)
                }
                repos?.filter { it != selectedRepo }?.forEach { repo: DroneRepo -> Unit
                    logger.debug("adding repo ${repo.full_name}")
                    reposCB.addItem(repo)
                }
            }

            override fun onFailure(call: Call<Array<DroneRepo>>, t: Throwable) {
                logger.debug("handling currentUserReposList failure")
                logger.error(t.message)
            }
        })
    }

    fun setRepoBuilds(selectedRepo: DroneRepo?) {
        logger.debug("setting repo builds")
        if (!this.droneAPI.hasRequiredSettings()) {
            logger.warn("missing required Drone settings")
            return
        }
        if (selectedRepo == null) {
            logger.warn("repo is null")
            return
        }
        val repos = this.droneToolWindow.repoBuildsList
        val listModel = DefaultListModel<DroneBuild>()
        repos.model = listModel
        this.droneAPI.getService().repoBuildsList(selectedRepo.owner, selectedRepo.name).enqueue(object : Callback<Array<DroneBuild>> {
            override fun onFailure(call: Call<Array<DroneBuild>>, t: Throwable) {
                logger.debug("handling repoBuildsList failure")
                logger.error(t.message)
            }

            override fun onResponse(call: Call<Array<DroneBuild>>, response: Response<Array<DroneBuild>>) {
                logger.debug("handling repoBuildsList response")
                response.body()?.forEach { build: DroneBuild -> Unit
                    logger.debug("adding ${selectedRepo.full_name} build ${build.number}")
                    listModel.addElement(build)
                }
            }
        })
    }

    fun clearBuildProcs() {
        logger.debug("clearing repoBuildProcsComboBox")
        this.droneToolWindow.repoBuildProcsComboBox.removeAllItems()
    }

    fun setBuildProcs(repo: DroneRepo?, build: DroneBuild?) {
        logger.debug("setting build procs")
        if (!this.droneAPI.hasRequiredSettings()) {
            logger.warn("missing required Drone settings")
            return
        }
        if (repo == null) {
            logger.warn("repo is null")
            return
        }
        if (build == null) {
            logger.warn("build is null")
            return
        }
        this.clearBuildProcs()
        this.droneAPI.getService().repoBuildInfo(repo.owner, repo.name, build.number).enqueue(object: Callback<DroneBuild> {
            override fun onFailure(call: Call<DroneBuild>, t: Throwable) {
                logger.debug("handling repoBuildInfo failure")
                logger.error(t.message)
            }

            override fun onResponse(call: Call<DroneBuild>, response: Response<DroneBuild>) {
                logger.debug("handling repoBuildInfo response")
                response.body()?.procs?.first()?.children?.forEach { proc: DroneProc -> Unit
                    logger.debug("adding ${repo.full_name} - ${build.number} - ${proc.name}")
                    droneToolWindow.repoBuildProcsComboBox.addItem(proc)
                }
            }
        })
    }

    fun clearBuildLogs() {
        logger.debug("clearing build logs")
        this.droneToolWindow.repoBuildLogsList.model = DefaultListModel<DroneLog>()
    }

    fun tailBuildLogs(repo: DroneRepo?, build: DroneBuild?, proc: DroneProc?) {
        logger.debug("tailing build logs")
        if (!this.droneAPI.hasRequiredSettings()) {
            logger.debug("missing required Drone settings")
            return
        }
        if (repo == null) {
            logger.warn("repo is null")
            return
        }
        if (build == null) {
            logger.warn("build is null")
            return
        }
        if (proc == null) {
            logger.warn("proc is null")
            return
        }
        val listModel = DefaultListModel<DroneLog>()
        this.droneToolWindow.repoBuildLogsList.model = listModel
        this.droneAPI.getService().repoBuildLogs(repo.owner, repo.name, build.number, proc.pid).enqueue(object: Callback<Array<DroneLog>> {
            override fun onFailure(call: Call<Array<DroneLog>>, t: Throwable) {
                logger.debug("handling repoBuildLogs failure")
                logger.error(t.message)
            }

            override fun onResponse(call: Call<Array<DroneLog>>, response: Response<Array<DroneLog>>) {
                logger.debug("handling repoBuildLogs success")
                val logs = response.body()
                if (logs == null) {
                    logger.warn("response body null")
                    return
                }
                listModel.ensureCapacity(logs.size)
                logs.forEach { listModel.addElement(it) }
            }
        })
    }
}
