package com.samdfonseca.intellijDrone

import com.samdfonseca.intellijDrone.droneApi.DroneBuild
import java.awt.Component
import java.awt.Color
import java.awt.Font
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.swing.*

class BuildListCellRenderer : JLabel(), ListCellRenderer<DroneBuild> {
    val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")
    override fun getListCellRendererComponent(
        list: JList<out DroneBuild>?,
        build: DroneBuild?,
        index: Int,
        isSelected: Boolean,
        cellHasFocus: Boolean
    ): Component {
        if (isSelected) {
            this.isOpaque = true
            this.foreground = Color.WHITE
            this.background = Color.BLUE.darker()
            this.font = Font(this.font.name, Font.BOLD, this.font.size)
        } else {
            this.font = Font(this.font.name, Font.PLAIN, this.font.size)
            when (build?.status) {
                "running" -> {
                    this.background = Color.YELLOW.brighter()
                    this.foreground = list?.foreground
                }
                "failure" -> {
                    this.background = list?.background
                    this.foreground = Color.RED.darker()
                }
                else -> {
                    this.background = list?.background
                    this.foreground = list?.foreground
                }
            }
        }
        val createdAt = dateFormat.format(LocalDateTime.ofInstant(build?.createdAtInstant(), ZoneOffset.UTC))
        this.text = "${build?.number} [$createdAt] - ${build?.message}"
        return this
    }
}
