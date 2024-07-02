package com.github.sydowma.fastplugin.actions;

import com.github.sydowma.fastplugin.services.OpenAIService;
import com.github.sydowma.fastplugin.settings.SettingsState;
import com.github.sydowma.fastplugin.toolwindow.ChatGptToolWindowPanel;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class QuickPromptAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) return;

        String filePath = e.getDataContext().getData(com.intellij.openapi.actionSystem.CommonDataKeys.VIRTUAL_FILE).getPath();
        SettingsState settings = SettingsState.getInstance();
        String prompt = settings.prompt.replace("{filePath}", filePath);

        // Retrieve OpenAI API key from settings
        String apiKey = settings.apiKey;

        OpenAIService openAIService = new OpenAIService(apiKey);
        try {
            String response = openAIService.callOpenAI(prompt);

            // 显示响应内容到 Tool Window
            ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("ChatGPT");
            if (toolWindow != null) {
                Content content = toolWindow.getContentManager().getContent(0);
                if (content != null && content.getComponent() instanceof JScrollPane scrollPane) {
                    if (scrollPane.getViewport().getView() instanceof ChatGptToolWindowPanel chatGptToolWindowPanel) {
                        chatGptToolWindowPanel.displayResponse(response);
                        toolWindow.show();
                    }
                }
            }
        } catch (Exception ex) {
            Messages.showErrorDialog("Error calling OpenAI API: " + ex.getMessage(), "Error");
        }
    }
}
