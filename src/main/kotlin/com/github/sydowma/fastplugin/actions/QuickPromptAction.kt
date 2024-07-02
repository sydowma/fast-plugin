package com.github.sydowma.fastplugin.actions

import com.github.sydowma.fastplugin.services.OpenAIService
import com.github.sydowma.fastplugin.settings.SettingsState.Companion.instance
import com.github.sydowma.fastplugin.toolwindow.ChatGptToolWindowPanel
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowManager
import com.intellij.ui.content.Content
import java.io.BufferedReader
import java.io.InputStreamReader

class QuickPromptAction : AnAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE)
        if (virtualFile != null) {
            val fileContent = readFileContent(virtualFile)

            val settings = instance
            val prompt = settings.prompt?.replace("{filePath}", virtualFile.path)

            // Retrieve OpenAI API key from settings
            val apiKey = settings.apiKey

            if (apiKey.isNullOrEmpty()) {
                Messages.showErrorDialog("Please set your OpenAI API key in the settings.", "Error")
                return
            }

            // Trigger sendMessage with file content
            val toolWindow = ToolWindowManager.getInstance(project).getToolWindow("ChatGPT")
            if (toolWindow != null) {
                var panel: ChatGptToolWindowPanel? = null
                val contentManager = toolWindow.contentManager
                if (contentManager.contentCount == 0) {
                    panel = ChatGptToolWindowPanel(project)
                    val content: Content = contentManager.factory.createContent(panel.mainPanel, "", false)
                    contentManager.addContent(content)
                } else {
                    val content = contentManager.getContent(0)
                    if (content != null && content.component is ChatGptToolWindowPanel) {
                        panel = content.component as ChatGptToolWindowPanel
                    }
                }
                panel?.sendMessage("$prompt\n\n$fileContent")
                toolWindow.show()
            }
        } else {
            Messages.showErrorDialog("No file selected.", "Error")
        }
    }

    private fun readFileContent(file: VirtualFile): String {
        val content = StringBuilder()
        try {
            file.inputStream.use { inputStream ->
                BufferedReader(InputStreamReader(inputStream)).use { reader ->
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        content.append(line).append("\n")
                    }
                }
            }
        } catch (ex: Exception) {
            Messages.showErrorDialog("Error reading file content: ${ex.message}", "Error")
        }
        return content.toString()
    }
}
