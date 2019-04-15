package com.samdfonseca.intellijDrone

import com.intellij.openapi.wm.ToolWindow
import javax.swing.*
import java.util.Calendar


class RepoTreeToolWindow(val toolWindow: ToolWindow) {
    private val logger = getLogger(this)
    lateinit var refreshReposButton: JButton
    lateinit var hideToolWindowButton: JButton
    lateinit var currentDate: JLabel
    lateinit var currentTime: JLabel
    lateinit var timeZone: JLabel
    lateinit var repoTreeToolWindowPanel: JPanel

    init {
        logger.debug("init-ing RepoTreeToolWindow")
        this.hideToolWindowButton.addActionListener {
            logger.debug("hiding RepoTreeToolWindow instance")
            this.toolWindow.hide(null)
        }
        this.refreshReposButton.addActionListener { this.refresh() }
        this.refresh()
    }

    fun refresh() {
        logger.debug("refreshing RepoTreeToolWindow")
        val calendar = Calendar.getInstance()
        this.setCurrentDate(calendar)
        this.setCurrentTime(calendar)
        this.setTimeZone(calendar)
    }

    fun setCurrentDate(calendar: Calendar) {
        logger.debug("setting currentDate.text")
        val d = calendar.get(Calendar.DAY_OF_MONTH)
        val m = calendar.get(Calendar.MONTH)
        val y = calendar.get(Calendar.YEAR)
        this.currentDate.text = "$m/$d/$y"
    }

    fun setCurrentTime(calendar: Calendar) {
        logger.debug("setting currentTime.text")
        val mInt = calendar.get(Calendar.MINUTE)
        val m = if (mInt < 10) "0$mInt" else "$mInt"
        val hInt = calendar.get(Calendar.HOUR_OF_DAY)
        val h = if (hInt < 10) "0$hInt" else "$hInt"
        val sInt = calendar.get(Calendar.SECOND)
        val s = if (sInt < 10) "0$sInt" else "$sInt"
        this.currentTime.text = "$h:$m:$s"
    }

    fun setTimeZone(calendar: Calendar) {
        logger.debug("setting timeZone.text")
        val z = calendar.get(Calendar.ZONE_OFFSET) / 3600000
        this.timeZone.text = if (z > 0) "GMT+$z" else "GMT-$z"
    }

    fun getContent() = repoTreeToolWindowPanel
}
