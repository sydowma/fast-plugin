package com.github.sydowma.fastplugin.settings

import java.awt.BorderLayout
import javax.swing.*

class SettingsComponent {
    val panel: JPanel = JPanel(BorderLayout())
    private val apiKeyField = JTextField()
    private val promptField = JTextArea()
    private val defaultPromptComboBox = JComboBox<String>()

    init {
        panel.add(JLabel("OpenAI API Key:"), BorderLayout.NORTH)
        panel.add(apiKeyField, BorderLayout.NORTH)
        panel.add(JLabel("Prompts:"), BorderLayout.CENTER)
        panel.add(JScrollPane(promptField), BorderLayout.CENTER)
        panel.add(JLabel("Default Prompt:"), BorderLayout.SOUTH)
        panel.add(defaultPromptComboBox, BorderLayout.SOUTH)
    }

    fun isModified(settings: SettingsState): Boolean {
        return (apiKeyField.text != settings.apiKey
                || promptField.text != settings.prompt
                || defaultPromptComboBox.selectedItem != settings.defaultPrompt)
    }

    fun apply(settings: SettingsState) {
        settings.apiKey = apiKeyField.text
        settings.prompt = promptField.text
        settings.defaultPrompt = (defaultPromptComboBox.selectedItem as String)
    }

    fun reset(settings: SettingsState) {
        apiKeyField.text = settings.apiKey
        promptField.text = settings.prompt
        defaultPromptComboBox.selectedItem = settings.defaultPrompt
    }
}

