package com.samdfonseca.intellijDrone

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
    lateinit var availableReposComboBox: JComboBox<DroneRepo>
    lateinit var repoBuildsScrollPane: JScrollPane
    lateinit var repoBuildsList: JList<DroneBuild>
    lateinit var repoBuildProcsComboBox: JComboBox<DroneProc>
    lateinit var repoBuildLogsScrollPane: JScrollPane
    lateinit var repoBuildLogsList: JList<DroneLog>
    lateinit var buildLogsToolBar: JToolBar
    lateinit var buildLogsRefreshButton: JButton

    init {
        logger.debug("init-ing")
        this.availableReposComboBox.renderer = ReposListCellRenderer()
        this.repoBuildsList.cellRenderer = BuildListCellRenderer()
        this.repoBuildsList.selectionMode = ListSelectionModel.SINGLE_SELECTION
        this.repoBuildProcsComboBox.renderer = ProcsListCellRenderer()
        this.repoBuildLogsList.cellRenderer = BuildLogsCellRenderer()
        this.availableReposComboBox.addActionListener {
            logger.debug("availableReposComboBox ActionListener fired")
            this.droneToolWindowApiService.setRepoBuilds(this.selectedRepo())
        }
        this.refreshReposButton.addActionListener {
            this.droneToolWindowApiService.setRepos(this.selectedRepo())
        }
        this.refreshBuildsButton.addActionListener {
            this.droneToolWindowApiService.setRepoBuilds(this.selectedRepo())
        }
        this.repoBuildsList.addListSelectionListener { e: ListSelectionEvent -> Unit
            if (!e.valueIsAdjusting) this.droneToolWindowApiService.setBuildProcs(this.selectedRepo(), this.selectedBuild())
        }
        this.repoBuildProcsComboBox.addActionListener {
            this.droneToolWindowApiService.tailBuildLogs(this.selectedRepo(), this.selectedBuild(), this.selectedProc())
        }
        this.buildLogsRefreshButton.addActionListener {
            this.droneToolWindowApiService.tailBuildLogs(this.selectedRepo(), this.selectedBuild(), this.selectedProc())
        }
        this.droneToolWindowApiService.setRepos(null)
    }

    fun selectedRepo() = if (this.availableReposComboBox.selectedItem != null) this.availableReposComboBox.selectedItem as DroneRepo else null
    fun selectedBuild() = this.repoBuildsList.selectedValue
    fun selectedProc() = if (this.repoBuildProcsComboBox.selectedItem != null) this.repoBuildProcsComboBox.selectedItem as DroneProc else null
    fun getContent() = toolWindowPanel
}
