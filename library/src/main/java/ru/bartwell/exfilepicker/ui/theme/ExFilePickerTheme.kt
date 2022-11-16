package ru.bartwell.exfilepicker.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import ru.bartwell.exfilepicker.data.config.DarkMode
import ru.bartwell.exfilepicker.data.config.UiConfig

internal val LocalColors = staticCompositionLocalOf<ExFilePickerColorScheme> {
    error("No ColorsConfig provided")
}

@Composable
internal fun ExFilePickerTheme(
    uiConfig: UiConfig,
    content: @Composable () -> Unit,
) {
    val darkTheme = when (uiConfig.darkMode) {
        DarkMode.SYSTEM -> isSystemInDarkTheme()
        DarkMode.DARK -> true
        DarkMode.LIGHT -> false
    }
    val colorScheme = when {
        uiConfig.useDynamicColors && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            val scheme = if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            scheme.toExFilePickerColorScheme()
        }

        darkTheme -> uiConfig.darkModeColors.toExFilePickerColorScheme()
        else -> uiConfig.lightModeColors.toExFilePickerColorScheme()
    }

    MaterialTheme(
        typography = Typography,
    ) {
        CompositionLocalProvider(
            LocalColors provides colorScheme,
            LocalContentColor provides colorScheme.itemTitle,
            LocalTextStyle provides MaterialTheme.typography.titleMedium,
            content = content,
        )
    }
}
