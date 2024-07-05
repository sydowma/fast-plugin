package com.github.sydowma.fastplugin.toolwindow

import com.github.sydowma.fastplugin.services.OpenAIService
import com.github.sydowma.fastplugin.settings.SettingsState
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.JTextField
import java.awt.BorderLayout
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

class ChatGptToolWindowPanel(private val project: Project) : JPanel() {
    private val textArea: JTextArea
    private val inputField: JTextField
    private val sendButton: JButton

    init {
        layout = BorderLayout()
        textArea = JTextArea().apply {
            isEditable = false
        }
        inputField = JTextField()
        sendButton = JButton("Send")

        val inputPanel = JPanel(BorderLayout()).apply {
            add(inputField, BorderLayout.CENTER)
            add(sendButton, BorderLayout.EAST)
        }

        add(JScrollPane(textArea), BorderLayout.CENTER)
        add(inputPanel, BorderLayout.SOUTH)

        inputField.addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                if (e.keyCode == KeyEvent.VK_ENTER) {
                    sendButton.doClick()
                }
            }
        })

        sendButton.addActionListener {
            val text = inputField.text
            if (text.isNotEmpty()) {
                sendMessage(text)
                inputField.text = ""
            }
        }
    }

    private fun displayResponse(response: String?) {
        textArea.append(response)
    }

    fun sendMessage(message: String) {
        val settings = SettingsState.instance
        val apiKey = settings.apiKey

        if (apiKey.isNullOrEmpty()) {
            displayResponse("Please set your OpenAI API key in the settings.")
            return
        }

        val openAIService = OpenAIService(settings)
        ApplicationManager.getApplication().executeOnPooledThread {
            openAIService.callOpenAI(message) { response ->
                ApplicationManager.getApplication().invokeLater {
                    displayResponse(response)
                }
            }
        }
    }

    val mainPanel: JPanel
        get() = this
}
