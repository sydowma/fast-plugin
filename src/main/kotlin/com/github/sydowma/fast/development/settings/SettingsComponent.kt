package com.github.sydowma.fast.development.settings

import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.*

class SettingsComponent {
    val panel: JPanel = JPanel(BorderLayout())
    private val apiKeyField = JTextField()
    private val promptField = JTextArea()
    private val defaultPromptComboBox = JComboBox<String>()

    private val proxyProtocolField = JComboBox(arrayOf("HTTP", "SOCKS"))
    private val proxyAddressField = JTextField()
    private val proxyPortField = JTextField()

    init {
        val tabbedPane = JTabbedPane()

        // API Settings Panel
        val apiSettingsPanel = JPanel(GridBagLayout())
        val constraints = GridBagConstraints()

        constraints.fill = GridBagConstraints.HORIZONTAL
        constraints.gridx = 0
        constraints.gridy = 0
        apiSettingsPanel.add(JLabel("OpenAI API Key:"), constraints)

        constraints.gridx = 1
        apiSettingsPanel.add(apiKeyField, constraints)

        constraints.gridx = 0
        constraints.gridy = 1
        apiSettingsPanel.add(JLabel("Prompts:"), constraints)

        constraints.gridx = 1
        constraints.gridy = 1
        constraints.weightx = 1.0
        constraints.weighty = 1.0
        constraints.fill = GridBagConstraints.BOTH
        apiSettingsPanel.add(JScrollPane(promptField), constraints)

        constraints.gridx = 0
        constraints.gridy = 2
        constraints.weightx = 0.0
        constraints.weighty = 0.0
        constraints.fill = GridBagConstraints.HORIZONTAL
        apiSettingsPanel.add(JLabel("Default Prompt:"), constraints)

        constraints.gridx = 1
        apiSettingsPanel.add(defaultPromptComboBox, constraints)

        // Proxy Settings Panel
        val proxySettingsPanel = JPanel(GridBagLayout())

        constraints.gridx = 0
        constraints.gridy = 0
        proxySettingsPanel.add(JLabel("Proxy Protocol:"), constraints)

        constraints.gridx = 1
        proxySettingsPanel.add(proxyProtocolField, constraints)

        constraints.gridx = 0
        constraints.gridy = 1
        proxySettingsPanel.add(JLabel("Proxy Address:"), constraints)

        constraints.gridx = 1
        proxySettingsPanel.add(proxyAddressField, constraints)

        constraints.gridx = 0
        constraints.gridy = 2
        proxySettingsPanel.add(JLabel("Proxy Port:"), constraints)

        constraints.gridx = 1
        proxySettingsPanel.add(proxyPortField, constraints)

        tabbedPane.addTab("API Settings", apiSettingsPanel)
        tabbedPane.addTab("Proxy Settings", proxySettingsPanel)

        panel.add(tabbedPane, BorderLayout.CENTER)
    }

    fun isModified(settings: SettingsState): Boolean {
        return (apiKeyField.text != (settings.apiKey ?: "")
                || promptField.text != (settings.prompt ?: "")
                || defaultPromptComboBox.selectedItem != (settings.defaultPrompt ?: "")
                || proxyProtocolField.selectedItem != (settings.proxyProtocol ?: "")
                || proxyAddressField.text != (settings.proxyAddress ?: "")
                || proxyPortField.text.toIntOrNull() != settings.proxyPort)
    }

    fun apply(settings: SettingsState) {
        settings.apiKey = apiKeyField.text ?: ""
        settings.prompt = promptField.text ?: ""
        settings.defaultPrompt = defaultPromptComboBox.selectedItem as? String ?: ""
        settings.proxyProtocol = proxyProtocolField.selectedItem as? String ?: ""
        settings.proxyAddress = proxyAddressField.text ?: ""
        settings.proxyPort = proxyPortField.text.toIntOrNull() ?: 0
    }

    fun reset(settings: SettingsState) {
        apiKeyField.text = settings.apiKey ?: ""
        promptField.text = settings.prompt ?: ""
        defaultPromptComboBox.selectedItem = settings.defaultPrompt ?: ""
        proxyProtocolField.selectedItem = settings.proxyProtocol ?: ""
        proxyAddressField.text = settings.proxyAddress ?: ""
        proxyPortField.text = settings.proxyPort.toString()
    }
}
