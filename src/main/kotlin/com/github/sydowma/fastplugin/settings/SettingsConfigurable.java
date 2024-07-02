package com.github.sydowma.fastplugin.settings;


import com.intellij.openapi.options.Configurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SettingsConfigurable implements Configurable {
    private SettingsComponent settingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Quick Prompt Settings";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingsComponent = new SettingsComponent();
        return settingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        return settingsComponent.isModified();
    }

    @Override
    public void apply() {
        settingsComponent.apply();
    }

    @Override
    public void reset() {
        settingsComponent.reset();
    }

    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}
