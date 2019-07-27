package com.samdfonseca.intellijDrone

import com.samdfonseca.intellijDrone.droneApi.DroneLog
import java.awt.Component
import java.awt.Color
import java.awt.Font
import javax.swing.*

class BuildLogsCellRenderer : JLabel(), ListCellRenderer<DroneLog> {
    override fun getListCellRendererComponent(
        logsList: JList<out DroneLog>?,
        log: DroneLog?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        this.isOpaque = true
        this.background = Color.WHITE
        if (log!!.isInput()) {
            this.font = Font(this.font.name, Font.BOLD, this.font.size)
        } else {
            this.font = Font(this.font.name, Font.PLAIN, this.font.size)
        }
        this.text = log.out
        return this
    }
}
