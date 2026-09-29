package io.github.toyota32k.lib.themes

object BuiltInThemeList : IThemeList {
    override val themes : List<ThemeData> = listOf(
        ThemeData("Default", R.style.DynamicTheme, null, null),
        ThemeData("Cherry", R.style.CherryTheme, R.style.CherryTheme_MediumContrast, R.style.CherryTheme_HighContrast),
        ThemeData("Grape", R.style.GrapeTheme, R.style.GrapeTheme_MediumContrast, R.style.GrapeTheme_HighContrast),
        ThemeData("Blueberry", R.style.BlueberryTheme, R.style.BlueberryTheme_MediumContrast, R.style.BlueberryTheme_HighContrast),
        ThemeData("Melon", R.style.MelonTheme, R.style.MelonTheme_MediumContrast, R.style.MelonTheme_HighContrast),
        ThemeData("Orange", R.style.OrangeTheme, R.style.OrangeTheme_MediumContrast, R.style.OrangeTheme_HighContrast),
        ThemeData("Soda", R.style.SodaTheme, R.style.SodaTheme_MediumContrast, R.style.SodaTheme_HighContrast),
    )
}