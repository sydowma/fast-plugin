package com.github.sydowma.fast.development.settings

import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import org.jetbrains.annotations.Nls
import javax.swing.JComponent

class SettingsConfigurable : Configurable {
    private var settingsComponent: SettingsComponent? = null

    @Nls(capitalization = Nls.Capitalization.Title)
    override fun getDisplayName(): String {
        return "Quick Prompt Settings"
    }

    override fun createComponent(): JComponent {
        settingsComponent = SettingsComponent()
        return settingsComponent!!.panel
    }

    override fun isModified(): Boolean {
        val settings = SettingsState.instance
        return settingsComponent!!.isModified(settings)
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        val settings = SettingsState.instance
        settingsComponent!!.apply(settings)
    }

    override fun reset() {
        val settings = SettingsState.instance
        settingsComponent!!.reset(settings)
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }
}
