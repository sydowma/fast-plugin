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

    fun displayResponse(response: String?) {
        textArea.append(response + "\n")
    }

    private fun sendMessage(message: String) {
        val settings = SettingsState.instance
        val apiKey = settings.apiKey

        if (apiKey.isNullOrEmpty()) {
            displayResponse("Please set your OpenAI API key in the settings.")
            return
        }

        val openAIService = OpenAIService(apiKey)
        ApplicationManager.getApplication().executeOnPooledThread {
            try {
                val response = openAIService.callOpenAI(message)
                ApplicationManager.getApplication().invokeLater {
                    displayResponse(response)
                }
            } catch (ex: Exception) {
                ApplicationManager.getApplication().invokeLater {
                    displayResponse("Error calling OpenAI API: ${ex.message}")
                }
            }
        }
    }

    val mainPanel: JPanel
        get() = this
}