package com.samdfonseca.intellijDrone.project.toolwindow

import com.intellij.ui.treeStructure.SimpleTree
import com.samdfonseca.intellijDrone.project.model.DroneProject
import javax.swing.tree.DefaultMutableTreeNode

class DroneProjectsTree: SimpleTree() {
    val selectedProject: DroneProject? get() {
        val path = selectionPath ?: return null
        if (path.pathCount < 2) return null
        val treeNode = path.getPathComponent(1) as? DefaultMutableTreeNode ?: return null
        return (treeNode.userObject as? DroneProject)
    }
}
