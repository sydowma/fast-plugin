package com.github.sydowma.fastplugin.toolwindow

import java.awt.BorderLayout
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea

class ChatGptToolWindowPanel : JPanel() {
    private val textArea: JTextArea

    init {
        layout = BorderLayout()
        textArea = JTextArea()
        add(JScrollPane(textArea), BorderLayout.CENTER)
    }

    fun displayResponse(response: String?) {
        textArea.text = response
    }

    val mainPanel: JPanel
        get() = this
}