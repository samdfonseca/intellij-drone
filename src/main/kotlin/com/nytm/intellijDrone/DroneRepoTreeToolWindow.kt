package com.nytm.intellijDrone

import com.intellij.execution.filters.TextConsoleBuilder
import com.nytm.intellijDrone.droneApi.DroneAPI
import com.nytm.intellijDrone.droneApi.DroneAPIService
import com.intellij.openapi.wm.ToolWindow
import com.nytm.intellijDrone.droneApi.DroneBuild
import com.nytm.intellijDrone.droneApi.DroneRepo
import retrofit2.Call
import java.awt.event.ActionEvent
import javax.swing.*
import retrofit2.Callback
import retrofit2.Response
import javax.swing.event.ListSelectionEvent
import javax.swing.event.ListSelectionListener

class DroneRepoTreeToolWindow(val toolWindow: ToolWindow, private val settings: DroneSettingsProvider, private val consoleBuilder: TextConsoleBuilder) {
    private val logger = getLogger(this)
    private val droneAPI = DroneAPI(this.settings)
    lateinit var toolWindowPanel: JPanel
    lateinit var panelToolBar: JToolBar
    lateinit var refreshReposButton: JButton
    lateinit var refreshBuildsButton: JButton
    lateinit var setProjectRepoButton: JButton
    lateinit var buildLogsButton: JButton
    lateinit var availableReposComboBox: JComboBox<DroneRepo>
    lateinit var repoBuildsScrollPane: JScrollPane
    lateinit var repoBuildsList: JList<DroneBuild>

    init {
        logger.debug("init-ing")
        this.availableReposComboBox.renderer = ReposListCellRenderer()
        this.repoBuildsList.cellRenderer = BuildListCellRenderer()
        this.repoBuildsList.selectionMode = ListSelectionModel.SINGLE_SELECTION
        this.buildLogsButton.isEnabled = false
        this.availableReposComboBox.addActionListener { e: ActionEvent -> Unit
            logger.debug("availableReposComboBox ActionListener fired")
            val selectedRepo = this.availableReposComboBox.selectedItem
            if (selectedRepo != null) this.updateBuilds(selectedRepo as DroneRepo)
        }
        this.refreshReposButton.addActionListener {
            this.updateAvailableRepos()
        }
        this.refreshBuildsButton.addActionListener {
            this.updateBuilds(this.availableReposComboBox.selectedItem as DroneRepo)
        }
        this.setProjectRepoButton.addActionListener {
            this.settings.repo = (this.availableReposComboBox.selectedItem as DroneRepo).full_name
        }
        this.buildLogsButton.addActionListener {
            // TODO: add build log console view
        }
        this.repoBuildsList.addListSelectionListener {
            if (this.repoBuildsList.selectedIndex != -1) {
                this.buildLogsButton.isEnabled = true
            }
        }
        this.updateAvailableRepos()
    }

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
