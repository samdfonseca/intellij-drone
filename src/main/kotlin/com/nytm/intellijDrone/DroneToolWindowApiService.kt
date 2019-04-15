package com.nytm.intellijDrone

import com.nytm.intellijDrone.droneApi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.swing.DefaultListModel

class DroneToolWindowApiService(val droneRepoTreeToolWindow: DroneRepoTreeToolWindow, val droneAPI: DroneAPI): Logger {
    init {
        logger.debug("init-ing")
    }

    fun setRepos(selectedRepo: DroneRepo?) {
        if (!this.droneAPI.hasRequiredSettings()) {
            logger.warn("missing required Drone settings")
            return
        }
        logger.debug("setting repos")
        val reposCB = this.droneRepoTreeToolWindow.availableReposComboBox
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
        if (!this.droneAPI.hasRequiredSettings()) {
            logger.warn("missing required Drone settings")
            return
        }
        if (selectedRepo == null) {
            logger.warn("repo is null")
            return
        }
        logger.debug("setting repo builds")
        val repos = this.droneRepoTreeToolWindow.repoBuildsList
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
        this.droneRepoTreeToolWindow.repoBuildProcsComboBox.removeAllItems()
    }

    fun setBuildProcs(repo: DroneRepo?, build: DroneBuild?) {
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
        logger.debug("setting build ${build.number} procs")
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
                    droneRepoTreeToolWindow.repoBuildProcsComboBox.addItem(proc)
                }
            }
        })
    }

    fun tailBuildLogs(repo: DroneRepo, build: DroneBuild, proc: DroneProc) {
        if (!this.droneAPI.hasRequiredSettings()) {
            logger.debug("missing required Drone settings")
            return
        }
        logger.debug("tailing build ${build.number} logs")
        val listModel = DefaultListModel<DroneLog>()
        this.droneRepoTreeToolWindow.repoBuildLogsList.model = listModel
        this.droneAPI.getService().repoBuildLogs(repo.owner, repo.name, build.number, proc.pid).enqueue(object: Callback<Array<DroneLog>> {
            override fun onFailure(call: Call<Array<DroneLog>>, t: Throwable) {
                logger.debug("handling repoBuildLogs failure")
                logger.error(t.message)
            }

            override fun onResponse(call: Call<Array<DroneLog>>, response: Response<Array<DroneLog>>) {
                logger.debug("handling repoBuildLogs success")
                val logs = response.body()
                listModel.addAll(logs?.sliceArray(IntRange(listModel.size(), logs.size))?.asList())
            }
        })
    }
}
