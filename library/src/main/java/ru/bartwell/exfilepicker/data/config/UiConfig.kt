package ru.bartwell.exfilepicker.data.config

data class UiConfig(
    val darkMode: DarkMode = DarkMode.SYSTEM,
    val useDynamicColors: Boolean = false,
    val darkModeColors: ColorsConfig = ColorsConfig.DARK,
    val lightModeColors: ColorsConfig = ColorsConfig.LIGHT,
)
