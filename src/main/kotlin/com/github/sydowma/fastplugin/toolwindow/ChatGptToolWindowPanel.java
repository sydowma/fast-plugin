package com.github.sydowma.fastplugin.toolwindow;

import javax.swing.*;
import java.awt.*;

public class ChatGptToolWindowPanel extends JPanel {
    private JTextArea textArea;

    public ChatGptToolWindowPanel() {
        setLayout(new BorderLayout());
        textArea = new JTextArea();
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }

    public void displayResponse(String response) {
        textArea.setText(response);
    }

    public JPanel getMainPanel() {
        return this;
    }
}