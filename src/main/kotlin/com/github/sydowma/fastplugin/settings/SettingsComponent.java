package com.github.sydowma.fastplugin.settings;

import javax.swing.*;
import java.awt.*;

public class SettingsComponent {
    private JPanel mainPanel;
    private JTextField apiKeyField;
    private JTextArea promptField;
    private JComboBox<String> defaultPromptComboBox;

    public SettingsComponent() {
        mainPanel = new JPanel(new BorderLayout());
        apiKeyField = new JTextField();
        promptField = new JTextArea();
        defaultPromptComboBox = new JComboBox<>();

        mainPanel.add(new JLabel("OpenAI API Key:"), BorderLayout.NORTH);
        mainPanel.add(apiKeyField, BorderLayout.NORTH);
        mainPanel.add(new JLabel("Prompts:"), BorderLayout.CENTER);
        mainPanel.add(new JScrollPane(promptField), BorderLayout.CENTER);
        mainPanel.add(new JLabel("Default Prompt:"), BorderLayout.SOUTH);
        mainPanel.add(defaultPromptComboBox, BorderLayout.SOUTH);
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public boolean isModified() {
        // Implement logic to check if settings are modified
        return true;
    }

    public void apply() {
        // Implement logic to save settings
    }

    public void reset() {
        // Implement logic to reset settings
    }
}

