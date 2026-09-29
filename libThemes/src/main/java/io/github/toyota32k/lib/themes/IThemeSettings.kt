package io.github.toyota32k.lib.themes


interface IThemeSettings {
    val themeDelegate: ThemeDelegate
    val colorVariationList: IThemeList
    val themeData: ThemeData
    val contrastLevel: ContrastLevel
    val dayNightMode: NightMode

    fun update(themeData: ThemeData?, contrastLevel: ContrastLevel?, dayNightMode: NightMode?)
}