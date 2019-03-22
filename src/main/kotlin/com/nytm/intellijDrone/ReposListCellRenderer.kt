package com.nytm.intellijDrone

import com.nytm.intellijDrone.droneApi.DroneRepo
import java.awt.Component
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.ListCellRenderer

class ReposListCellRenderer : JLabel(), ListCellRenderer<DroneRepo> {
    override fun getListCellRendererComponent(
        list: JList<out DroneRepo>?,
        repo: DroneRepo?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        if (isSelected) {
            this.background = list?.selectionBackground
            this.foreground = list?.selectionForeground
        } else {
            this.background = list?.background
            this.foreground = list?.foreground
        }
        this.text = repo?.full_name
        return this
    }
}
