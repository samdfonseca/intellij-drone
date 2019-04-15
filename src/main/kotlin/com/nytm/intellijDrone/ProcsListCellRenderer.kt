package com.nytm.intellijDrone

import com.nytm.intellijDrone.droneApi.DroneProc
import java.awt.Component
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.ListCellRenderer

class ProcsListCellRenderer : JLabel(), ListCellRenderer<DroneProc> {
    override fun getListCellRendererComponent(
        procsList: JList<out DroneProc>?,
        selectedProc: DroneProc?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        if (isSelected) {
            this.background = procsList?.selectionBackground
            this.foreground = procsList?.selectionForeground
        } else {
            this.background = procsList?.background
            this.foreground = procsList?.foreground
        }
        this.text = selectedProc?.toDisplayString(includePid = false)
        return this
    }
}
