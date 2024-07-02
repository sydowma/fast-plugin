package com.github.sydowma.fastplugin.toolwindow;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class ChatGptToolWindow implements ToolWindowFactory, DumbAware {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        ChatGptToolWindowPanel panel = new ChatGptToolWindowPanel();
        toolWindow.getContentManager().addContent(toolWindow.getContentManager().getFactory().createContent(panel.getMainPanel(), "", false));
    }

}
