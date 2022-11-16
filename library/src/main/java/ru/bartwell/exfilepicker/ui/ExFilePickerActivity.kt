package ru.bartwell.exfilepicker.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.bartwell.exfilepicker.ExFilePicker
import ru.bartwell.exfilepicker.ui.theme.ExFilePickerTheme
import ru.bartwell.exfilepicker.ui.theme.LocalColors

class ExFilePickerActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val exFilePickerConfig = ExFilePicker.configHolder
        setContent {
            ExFilePickerTheme(uiConfig = exFilePickerConfig.uiConfig) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = LocalColors.current.background
                ) {
                    val systemUiController = rememberSystemUiController()
                    val statusBarColor = LocalColors.current.statusBar
                    systemUiController.setStatusBarColor(statusBarColor, statusBarColor.luminance() > 0.5f)
                    val navigationBarColor = LocalColors.current.navigationBar
                    systemUiController.setNavigationBarColor(navigationBarColor, navigationBarColor.luminance() > 0.5f)
                    MainScreen(exFilePickerConfig = exFilePickerConfig)
                }
            }
        }
    }
}
