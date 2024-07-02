package com.github.sydowma.fastplugin.settings

import com.github.sydowma.fastplugin.settings.SettingsState.Companion.instance
import com.intellij.openapi.options.Configurable
import com.intellij.openapi.options.ConfigurationException
import org.jetbrains.annotations.Nls
import javax.swing.JComponent


class SettingsConfigurable : Configurable {
    private var settingsComponent: SettingsComponent? = null

    override fun getDisplayName(): @Nls(capitalization = Nls.Capitalization.Title) String {
        return "Quick Prompt Settings"
    }

    override fun createComponent(): JComponent? {
        settingsComponent = SettingsComponent()
        return settingsComponent!!.panel
    }

    override fun isModified(): Boolean {
        val settings = instance
        return settingsComponent!!.isModified(settings)
    }

    @Throws(ConfigurationException::class)
    override fun apply() {
        val settings = instance
        settingsComponent!!.apply(settings)
    }

    override fun reset() {
        val settings = instance
        settingsComponent!!.reset(settings)
    }

    override fun disposeUIResources() {
        settingsComponent = null
    }
}
