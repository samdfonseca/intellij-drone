package com.samdfonseca.intellijDrone

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import com.intellij.execution.filters.TextConsoleBuilder
import com.intellij.openapi.wm.ToolWindow
import com.samdfonseca.intellijDrone.droneApi.*
import retrofit2.Call
import java.awt.event.ActionEvent
import javax.swing.*
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class DroneToolWindow(val toolWindow: ToolWindow, private val settings: DroneSettingsProvider, private val consoleBuilder: TextConsoleBuilder): Logger {
    private val droneAPI = DroneAPI(this.settings)
    private val droneToolWindowApiService = DroneToolWindowApiService(this, droneAPI)
    lateinit var toolWindowPanel: JPanel
    lateinit var panelToolBar: JToolBar
    lateinit var refreshReposButton: JButton
    lateinit var refreshBuildsButton: JButton
    lateinit var setProjectRepoButton: JButton
    lateinit var buildLogsButton: JButton
    lateinit var availableReposComboBox: JComboBox<DroneRepo>
    lateinit var repoBuildsScrollPane: JScrollPane
    lateinit var repoBuildsList: JList<DroneBuild>
    lateinit var repoBuildProcsComboBox: JComboBox<DroneProc>
    lateinit var repoBuildLogsScrollPane: JScrollPane
    lateinit var repoBuildLogsList: JList<DroneLog>

    init {
        logger.debug("init-ing")
        this.availableReposComboBox.renderer = ReposListCellRenderer()
        this.repoBuildsList.cellRenderer = BuildListCellRenderer()
        this.repoBuildsList.selectionMode = ListSelectionModel.SINGLE_SELECTION
        this.repoBuildProcsComboBox.renderer = ProcsListCellRenderer()
        this.repoBuildLogsList.cellRenderer = BuildLogsCellRenderer()
        this.buildLogsButton.isEnabled = false
        this.availableReposComboBox.addActionListener { e: ActionEvent -> Unit
            logger.debug("availableReposComboBox ActionListener fired")
            this.droneToolWindowApiService.setRepoBuilds(this.selectedRepo())
        }
        this.refreshReposButton.addActionListener {
            this.droneToolWindowApiService.setRepos(this.selectedRepo())
        }
        this.refreshBuildsButton.addActionListener {
            this.droneToolWindowApiService.setRepoBuilds(this.availableReposComboBox.selectedItem as DroneRepo)
        }
        this.setProjectRepoButton.addActionListener {
            this.settings.repo = GsonBuilder().setLenient().create().toJson(this.selectedRepo())
        }
        this.buildLogsButton.addActionListener {
            // TODO: add build log console view
        }
        this.repoBuildsList.addListSelectionListener { e: ListSelectionEvent -> Unit
            if (!e.valueIsAdjusting) this.droneToolWindowApiService.setBuildProcs(this.selectedRepo(), this.selectedBuild())
        }
        try {
            this.droneToolWindowApiService.setRepos(
                GsonBuilder().setLenient().create().fromJson(this.settings.repo, DroneRepo::class.java))
        } catch (err: JsonSyntaxException) {
            logger.error(err)
        }

        // TODO: add build logs
        val buildLogsListModel = DefaultListModel<DroneLog>()
        val dummyLog = DroneLog("Build logs go here", 0, "dummy", Optional.of(0))
        buildLogsListModel.addElement(dummyLog)
        this.repoBuildLogsList.model = buildLogsListModel
    }

    fun selectedRepo() = if (this.availableReposComboBox.selectedItem != null) this.availableReposComboBox.selectedItem as DroneRepo else null
    fun selectedBuild() = this.repoBuildsList.selectedValue

    fun updateAvailableRepos() {
        if (!this.droneAPI.hasRequiredSettings()) {
            logger.debug("missing required Drone settings")
            return
        }
        logger.debug("updating available repos")
        availableReposComboBox.removeAllItems()
        this.droneAPI.getService().currentUserReposList().enqueue(object : Callback<Array<DroneRepo>> {
            override fun onResponse(call: Call<Array<DroneRepo>>, response: Response<Array<DroneRepo>>) {
                logger.debug("handling currentUserReposList response")
                response.body()?.forEach { repo: DroneRepo -> Unit
                    logger.debug("adding repo ${repo.full_name}")
                    availableReposComboBox.addItem(repo)
                    if (settings.repo != "" && repo.full_name == settings.repo) availableReposComboBox.selectedItem = repo
                }
            }

            override fun onFailure(call: Call<Array<DroneRepo>>, t: Throwable) {
                logger.debug("handling currentUserReposList failure")
                logger.error(t.message)
            }
        })
    }

    fun updateBuilds(repo: DroneRepo) {
        if (!this.droneAPI.hasRequiredSettings()) {
            logger.debug("missing required Drone settings")
            return
        }
        logger.debug("updating repo builds")
        val listModel = DefaultListModel<DroneBuild>()
        repoBuildsList.model = listModel
        this.droneAPI.getService().repoBuildsList(repo.owner, repo.name).enqueue(object : Callback<Array<DroneBuild>> {
            override fun onFailure(call: Call<Array<DroneBuild>>, t: Throwable) {
                logger.debug("handling repoBuildsList failure")
                logger.error(t.message)
            }

            override fun onResponse(call: Call<Array<DroneBuild>>, response: Response<Array<DroneBuild>>) {
                logger.debug("handling repoBuildsList response")
                response.body()?.forEach { build: DroneBuild -> Unit
                    logger.debug("adding build ${build.number}")
                    listModel.addElement(build)
                }
            }
        })
    }

    fun getContent() = toolWindowPanel
}
