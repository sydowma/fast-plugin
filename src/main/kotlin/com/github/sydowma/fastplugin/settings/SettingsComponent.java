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

    public boolean isModified(SettingsState settings) {
        return !apiKeyField.getText().equals(settings.apiKey)
                || !promptField.getText().equals(settings.prompt)
                || !defaultPromptComboBox.getSelectedItem().equals(settings.defaultPrompt);
    }

    public void apply(SettingsState settings) {
        settings.apiKey = apiKeyField.getText();
        settings.prompt = promptField.getText();
        settings.defaultPrompt = (String) defaultPromptComboBox.getSelectedItem();
    }

    public void reset(SettingsState settings) {
        apiKeyField.setText(settings.apiKey);
        promptField.setText(settings.prompt);
        defaultPromptComboBox.setSelectedItem(settings.defaultPrompt);
    }
}

