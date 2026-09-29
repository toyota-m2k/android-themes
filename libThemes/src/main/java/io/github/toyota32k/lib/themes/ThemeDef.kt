package io.github.toyota32k.lib.themes

import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDelegate

enum class NightMode(@param:AppCompatDelegate.NightMode val mode:Int) {
    System(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM),
    Light(AppCompatDelegate.MODE_NIGHT_NO),
    Dark(AppCompatDelegate.MODE_NIGHT_YES),
    ;
    companion object {
        fun ofMode(@AppCompatDelegate.NightMode mode:Int): NightMode? {
            return NightMode.entries.firstOrNull { it.mode == mode }
        }
    }
}
enum class ContrastLevel {
    System,
    Normal,
    Medium,
    High,
    ;
    companion object {
        fun parse(name: String): ContrastLevel? {
            return entries.firstOrNull { it.name == name }
        }
    }
}

data class ThemeData (
    val label: String,
    @param:StyleRes val id: Int,
    @param:StyleRes val overlayMedium: Int?,
    @param:StyleRes val overlayHigh: Int?,
)

interface IThemeList {
    val themes: List<ThemeData>
    val defaultTheme get() = themes[0]
    fun themeOf(name: String): ThemeData {
        return themes.firstOrNull { it.label == name } ?: defaultTheme
    }
}
