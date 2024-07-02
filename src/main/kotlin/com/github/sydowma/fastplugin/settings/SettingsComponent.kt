package com.github.sydowma.fastplugin.settings

import java.awt.BorderLayout
import java.awt.GridBagLayout
import java.awt.GridBagConstraints
import javax.swing.*

class SettingsComponent {
    val panel: JPanel = JPanel(BorderLayout())
    private val apiKeyField = JTextField()
    private val promptField = JTextArea()
    private val defaultPromptComboBox = JComboBox<String>()

    init {
        val innerPanel = JPanel(GridBagLayout())
        val constraints = GridBagConstraints()

        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 0
        constraints.gridy = 0
        innerPanel.add(JLabel("OpenAI API Key:"), constraints)

        constraints.gridx = 1
        innerPanel.add(apiKeyField, constraints)

        constraints.gridx = 0
        constraints.gridy = 1
        innerPanel.add(JLabel("Prompts:"), constraints)

        constraints.gridx = 1
        constraints.gridy = 1
        constraints.weightx = 1.0
        constraints.weighty = 1.0
        constraints.fill = GridBagConstraints.BOTH
        innerPanel.add(JScrollPane(promptField), constraints)

        constraints.gridx = 0
        constraints.gridy = 2
        constraints.weightx = 0.0
        constraints.weighty = 0.0
        constraints.fill = GridBagConstraints.HORIZONTAL
        innerPanel.add(JLabel("Default Prompt:"), constraints)

        constraints.gridx = 1
        innerPanel.add(defaultPromptComboBox, constraints)

        panel.add(innerPanel, BorderLayout.CENTER)
    }

    fun isModified(settings: SettingsState): Boolean {
        return (apiKeyField.text != (settings.apiKey ?: "")
                || promptField.text != (settings.prompt ?: "")
                || defaultPromptComboBox.selectedItem != (settings.defaultPrompt ?: ""))
    }

    fun apply(settings: SettingsState) {
        settings.apiKey = apiKeyField.text ?: ""
        settings.prompt = promptField.text ?: ""
        settings.defaultPrompt = defaultPromptComboBox.selectedItem as? String ?: ""
    }

    fun reset(settings: SettingsState) {
        apiKeyField.text = settings.apiKey ?: ""
        promptField.text = settings.prompt ?: ""
        defaultPromptComboBox.selectedItem = settings.defaultPrompt ?: ""
    }
}
