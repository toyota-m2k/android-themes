package io.github.toyota32k.lib.themes

import android.app.Activity
import android.content.Context
import android.provider.Settings
import androidx.appcompat.app.AppCompatDelegate
import io.github.toyota32k.logger.UtLog
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
    val logger = UtLog("Theme",null, ThemeDelegate::class.java)

    val applicationContext: Context
        get() = UtLib.applicationContext

    var currentThemeId:Int = -1
        private set

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

    enum class ApplyMode {
        RECREATE,
        RESTART,
        IMMEDIATE,
        ;
        fun action(activity: Activity):Boolean {
            return when (this) {
                RECREATE -> {
                    activity.recreate()
                    true
                }
                RESTART -> {
                    activity.finish()
                    activity.startActivity(activity.intent)
                    true
                }
                IMMEDIATE -> { false }
            }
        }
    }

    fun applyTheme(activity: Activity, theme: ThemeData, contrastLevel: ContrastLevel, nightMode: NightMode, applyMode: ApplyMode= ApplyMode.RECREATE) {
        val newThemeId = resolveThemeId(theme, contrastLevel)
        if (currentThemeId!=newThemeId) {
            if (applyMode.action(activity)) {
                logger.info("applying theme: to be recreated or restarted")
                return
            }
        }

        currentThemeId = newThemeId
        activity.setTheme(newThemeId)
        AppCompatDelegate.setDefaultNightMode(nightMode.mode)
        logger.info("applied theme")
    }
}