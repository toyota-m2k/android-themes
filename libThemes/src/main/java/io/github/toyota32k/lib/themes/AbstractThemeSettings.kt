package io.github.toyota32k.lib.themes

import android.app.Activity
import androidx.fragment.app.FragmentActivity


abstract class AbstractThemeSettings(
    final override val themeDelegate: ThemeDelegate,
    final override val colorVariationList: IThemeList) : IThemeSettings {
    constructor(): this(ThemeDelegate.defaultDelegate, BuiltInThemeList)
    override var themeData: ThemeData = colorVariationList.themes[0]
    override var contrastLevel: ContrastLevel = ContrastLevel.System
    override var dayNightMode: NightMode = NightMode.System

    override fun update(themeData: ThemeData?, contrastLevel: ContrastLevel?, dayNightMode: NightMode?) {
        var changed = false
        if (themeData != null && this.themeData.id != themeData.id) {
            this.themeData = themeData
            changed = true
        }
        if (contrastLevel != null && this.contrastLevel!= contrastLevel) {
            this.contrastLevel = contrastLevel
            changed = true
        }
        if (dayNightMode != null && this.dayNightMode != dayNightMode) {
            this.dayNightMode = dayNightMode
            changed = true
        }
        if (changed) {
            save()
        }
    }

    /**
     * 設定値 (themeData, contrastLabel, dayNightMode) を永続化する。
     * アプリ（サブクラス）側で実装すること。
     */
    protected open fun save() {}

    fun applyTheme(activity: Activity) {
        themeDelegate.applyTheme(activity,themeData, contrastLevel, dayNightMode, ThemeDelegate.ApplyMode.NONE)
    }
}