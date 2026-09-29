package io.github.toyota32k.lib.themes

import io.github.toyota32k.dialog.task.UtDialogViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ColorVariationViewModel : UtDialogViewModel() {
    val dayNightMode = MutableStateFlow(NightMode.System)
    val themeInfo = MutableStateFlow(BuiltInThemeList.themes[0])
    val contrastLevel = MutableStateFlow(ContrastLevel.System)

    lateinit var settings: IThemeSettings
    fun initialize(settings: IThemeSettings) {
        this.settings = settings
        dayNightMode.value = settings.dayNightMode
        themeInfo.value = settings.themeData
        contrastLevel.value = settings.contrastLevel
    }

    fun save() {
        settings.update(themeInfo.value, contrastLevel.value, dayNightMode.value)
    }
}
