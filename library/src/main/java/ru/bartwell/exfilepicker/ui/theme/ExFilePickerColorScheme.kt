package ru.bartwell.exfilepicker.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import ru.bartwell.exfilepicker.data.config.ColorsConfig

internal data class ExFilePickerColorScheme(
    val toolbarBackground: Color,
    val toolbarTitle: Color,
    val background: Color,
    val itemTitle: Color,
    val itemSubtitle: Color,
    val checkboxChecked: Color,
    val checkboxUnchecked: Color,
    val statusBar: Color,
    val navigationBar: Color,
)

internal fun ColorScheme.toExFilePickerColorScheme() = ExFilePickerColorScheme(
    toolbarBackground = surface,
    toolbarTitle = inverseOnSurface,
    background = background,
    itemTitle = onBackground,
    itemSubtitle = onBackground,
    checkboxChecked = primary,
    checkboxUnchecked = secondary,
    statusBar = surface,
    navigationBar = surface,
)

internal fun ColorsConfig.toExFilePickerColorScheme() = ExFilePickerColorScheme(
    toolbarBackground = Color(toolbarBackground),
    toolbarTitle = Color(toolbarTitle),
    background = Color(background),
    itemTitle = Color(itemTitle),
    itemSubtitle = Color(itemSubtitle),
    checkboxChecked = Color(checkboxChecked),
    checkboxUnchecked = Color(checkboxUnchecked),
    statusBar = Color(statusBar),
    navigationBar = Color(navigationBar),
)
