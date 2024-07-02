package com.github.sydowma.fastplugin.toolwindow;

import javax.swing.*;
import java.awt.*;

public class ChatGptToolWindowPanel extends JPanel {
    private JTextArea textArea;
    private JPanel mainPanel;

    public ChatGptToolWindowPanel() {
        textArea = new JTextArea();
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void displayResponse(String response) {
        textArea.setText(response);
    }
}
