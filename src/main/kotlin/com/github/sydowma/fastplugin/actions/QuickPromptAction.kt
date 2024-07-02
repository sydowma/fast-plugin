package com.github.sydowma.fastplugin.actions

import com.github.sydowma.fastplugin.services.OpenAIService
import com.github.sydowma.fastplugin.settings.SettingsState.Companion.instance
import com.github.sydowma.fastplugin.toolwindow.ChatGptToolWindowPanel
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.wm.ToolWindowManager
import javax.swing.JScrollPane

class QuickPromptAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        val filePath = e.dataContext.getData(CommonDataKeys.VIRTUAL_FILE)!!
            .path
        val settings = instance
        val prompt = settings.prompt.replace("{filePath}", filePath)

        // Retrieve OpenAI API key from settings
        val apiKey = settings.apiKey

        val openAIService = OpenAIService(apiKey)
        try {
            val response = openAIService.callOpenAI(prompt)

            // 显示响应内容到 Tool Window
            val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("ChatGPT")
            if (toolWindow != null) {
                val content = toolWindow.contentManager.getContent(0)
                if (content != null && content.component is JScrollPane) {
                    toolWindow.show()
                }
            }
        } catch (ex: Exception) {
            Messages.showErrorDialog("Error calling OpenAI API: " + ex.message, "Error")
        }
    }
}
