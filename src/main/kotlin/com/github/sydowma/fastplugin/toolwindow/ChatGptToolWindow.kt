package com.github.sydowma.fastplugin.toolwindow

import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory

class ChatGptToolWindow : ToolWindowFactory, DumbAware {
    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = ChatGptToolWindowPanel(project)
        toolWindow.contentManager.addContent(
            toolWindow.contentManager.factory.createContent(
                panel.mainPanel,
                "",
                false
            )
        )
    }
}