package com.samdfonseca.intellijDrone.project.toolwindow

import com.intellij.openapi.ui.Messages.showInfoMessage
import com.intellij.openapi.Disposable
import com.intellij.ui.tree.AsyncTreeModel
import com.intellij.ui.tree.StructureTreeModel
import com.intellij.ui.treeStructure.CachingSimpleNode
import com.intellij.ui.treeStructure.SimpleNode
import com.intellij.ui.treeStructure.NullNode
import com.intellij.ui.treeStructure.SimpleTreeStructure
import com.intellij.ui.treeStructure.treetable.TreeTable
import com.intellij.util.ui.tree.TreeUtil
import com.samdfonseca.intellijDrone.icons.DroneIcons
import com.samdfonseca.intellijDrone.project.model.DroneProject

class DroneProjectTreeStructure(
    tree: DroneProjectsTree,
    parentDisposable: Disposable,
    private var droneProjects: List<DroneProject> = emptyList()
): SimpleTreeStructure() {
    private val treeModel = StructureTreeModel(this, parentDisposable)
    private var root = DroneSimpleNode.Root(droneProjects)

    init {
        tree.model = AsyncTreeModel(treeModel, parentDisposable)
    }
    override fun getRootElement(): Any = root

    sealed class DroneSimpleNode(parent: SimpleNode?): CachingSimpleNode(parent) {
        abstract fun toTestString(): String

        class Root(private val droneProjects: List<DroneProject>): DroneSimpleNode(null) {
            override fun getName(): String = ""
            override fun toTestString(): String = "Root"
            override fun buildChildren(): Array<SimpleNode> = droneProjects.map { Project(it, this) }.toTypedArray()
        }

        class Project(val droneProject: DroneProject, parent: SimpleNode): DroneSimpleNode(parent) {
            init {
                icon = DroneIcons.ICON
            }

            override fun getName(): String = droneProject.presentableName

            override fun toTestString(): String = "Project($name)"
            override fun buildChildren(): Array<SimpleNode> = arrayOf(NullNode())
        }
    }
}
