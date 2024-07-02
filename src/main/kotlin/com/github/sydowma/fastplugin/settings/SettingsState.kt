package com.github.sydowma.fastplugin.settings

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(name = "com.github.sydowma.fastplugin.settings.SettingsState", storages = [Storage("QuickPromptSettings.xml")])
class SettingsState : PersistentStateComponent<SettingsState?> {
    @JvmField
    var apiKey: String = ""
    @JvmField
    var prompt: String = ""
    @JvmField
    var defaultPrompt: String = ""

    override fun getState(): SettingsState? {
        return this
    }

    override fun loadState(state: SettingsState) {
        this.apiKey = state.apiKey
        this.prompt = state.prompt
        this.defaultPrompt = state.defaultPrompt
    }

    companion object {
        @JvmStatic
        val instance: SettingsState
            get() = ServiceManager.getService(
                SettingsState::class.java
            )
    }
}
