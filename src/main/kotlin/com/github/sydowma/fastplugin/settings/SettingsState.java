package com.github.sydowma.fastplugin.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "com.github.sydowma.fastplugin.settings.SettingsState",
        storages = {@Storage("QuickPromptSettings.xml")}
)
public class SettingsState implements PersistentStateComponent<SettingsState> {
    public String apiKey = "";
    public String prompt = "";
    public String defaultPrompt = "";

    @Nullable
    @Override
    public SettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull SettingsState state) {
        this.apiKey = state.apiKey;
        this.prompt = state.prompt;
        this.defaultPrompt = state.defaultPrompt;
    }

    public static SettingsState getInstance() {
        return ServiceManager.getService(SettingsState.class);
    }
}
