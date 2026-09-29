package io.github.toyota32k.lib.themes

import android.app.Activity
import android.content.Context
import android.provider.Settings
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentActivity
import io.github.toyota32k.utils.UtLib

class ThemeDelegate {
    companion object {
        /**
         * UtLib.initialize()を呼び出す
         * アプリがUtLibを直接リンクしない場合に利用する。
         */
        fun initialize(applicationContext: Context) {
            UtLib.initialize(applicationContext)
        }
        val defaultDelegate: ThemeDelegate = ThemeDelegate()
    }

    val applicationContext: Context
        get() = UtLib.applicationContext

    var currentThemeId:Int = -1
        private set

    val currentNightMode:NightMode get() = NightMode.ofMode(AppCompatDelegate.getDefaultNightMode()) ?: NightMode.System

    private fun getSystemContrastLevel(): ContrastLevel {
        return try {
            val isDaltonizerEnabled = Settings.Secure.getInt(
                applicationContext.contentResolver,
                "accessibility_display_daltonizer_enabled", 0
            ) == 1

            val daltonizerMode = Settings.Secure.getInt(
                applicationContext.contentResolver,
                "accessibility_display_daltonizer", -1
            )

            when {
                !isDaltonizerEnabled -> ContrastLevel.Normal
                daltonizerMode == 0 -> ContrastLevel.Medium
                daltonizerMode > 0 -> ContrastLevel.High
                else -> ContrastLevel.Normal
            }
        } catch (_: Exception) {
            ContrastLevel.Normal
        }
    }

    private fun resolveContrastLevel(contrastLevel: ContrastLevel): ContrastLevel {
        return if(contrastLevel== ContrastLevel.System) {
            getSystemContrastLevel()
        } else contrastLevel
    }

    private fun resolveThemeId(theme: ThemeData, contrastLevel: ContrastLevel): Int {
        return when (resolveContrastLevel(contrastLevel)) {
            ContrastLevel.High -> theme.overlayHigh
            ContrastLevel.Medium -> theme.overlayMedium
            else -> null
        } ?: theme.id
    }

    fun isThemeChanged(theme: ThemeData, contrastLevel: ContrastLevel): Boolean {
        return currentThemeId != resolveThemeId(theme, contrastLevel)
    }

    fun applyTheme(theme: ThemeData, contrastLevel: ContrastLevel, activity: Activity) {
        val themeId = resolveThemeId(theme, contrastLevel)
        activity.setTheme(themeId)
        currentThemeId = themeId
    }

    fun applyNightMode(nightMode: NightMode) {
        if(AppCompatDelegate.getDefaultNightMode()!=nightMode.mode) {
            AppCompatDelegate.setDefaultNightMode(nightMode.mode)
        }
    }
}