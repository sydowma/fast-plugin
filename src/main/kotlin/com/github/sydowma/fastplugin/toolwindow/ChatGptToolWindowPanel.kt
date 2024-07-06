package com.github.sydowma.fastplugin.toolwindow

import com.github.sydowma.fastplugin.services.OpenAIService
import com.github.sydowma.fastplugin.settings.SettingsState
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import java.awt.BorderLayout
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.*

class ChatGptToolWindowPanel(private val project: Project) : JPanel() {
    private val editorPane: JEditorPane
    private val inputField: JTextField
    private val sendButton: JButton
    private val responseBuffer = StringBuilder()

    init {
        layout = BorderLayout()
        editorPane = JEditorPane().apply {
            isEditable = false
            contentType = "text/html"
        }
        inputField = JTextField()
        sendButton = JButton("Send")

        val inputPanel = JPanel(BorderLayout()).apply {
            add(inputField, BorderLayout.CENTER)
            add(sendButton, BorderLayout.EAST)
        }

        add(JScrollPane(editorPane), BorderLayout.CENTER)
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
        if (response != null) {
            responseBuffer.append(response)
            // Perform conversion and display in small batches to avoid long delays
            if (responseBuffer.length > 20) {
                val html = convertMarkdownToHtml(responseBuffer.toString())
                editorPane.text = html
            }
        }
    }

    private fun finalizeDisplay() {
        if (responseBuffer.isNotEmpty()) {
            val html = convertMarkdownToHtml(responseBuffer.toString())
            editorPane.text = editorPane.text + html + "<br>"
            responseBuffer.setLength(0) // Clear buffer after converting and displaying
        }
    }

    private fun convertMarkdownToHtml(markdown: String): String {
        val parser = Parser.builder().build()
        val document = parser.parse(markdown)
        val renderer = HtmlRenderer.builder().build()
        return renderer.render(document)
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
            openAIService.callOpenAI(message) { response, isFinal ->
                ApplicationManager.getApplication().invokeLater {
                    if (isFinal) {
                        finalizeDisplay()
                    } else {
                        displayResponse(response)
                    }
                }
            }
        }
    }

    val mainPanel: JPanel
        get() = this
}
