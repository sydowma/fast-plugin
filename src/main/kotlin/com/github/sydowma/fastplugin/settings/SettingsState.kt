package com.github.sydowma.fastplugin.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

@State(name = "com.github.sydowma.fastplugin.settings.SettingsState", storages = [Storage("QuickPromptSettings.xml")])
@Service
class SettingsState : PersistentStateComponent<SettingsState> {
    @JvmField
    var apiKey: String? = ""
    @JvmField
    var prompt: String? = ""
    @JvmField
    var defaultPrompt: String? = ""

    override fun getState(): SettingsState {
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
            get() = ApplicationManager.getApplication().getService(SettingsState::class.java)
    }

}
