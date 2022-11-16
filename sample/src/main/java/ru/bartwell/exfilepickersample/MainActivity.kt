package ru.bartwell.exfilepickersample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.bartwell.exfilepicker.ExFilePicker
import ru.bartwell.exfilepicker.data.config.Config
import ru.bartwell.exfilepicker.data.config.DarkMode
import ru.bartwell.exfilepicker.data.config.UiConfig
import ru.bartwell.exfilepickersample.ui.theme.ExFilePickerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExFilePickerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ScreenContent()
                }
            }
        }
    }
}

@Composable
private fun ScreenContent() {
    val showPicker = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val exFilePicker = ExFilePicker()
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        showPicker.value = false
        val result = exFilePicker.getResult(it.data)?.toList().toString()
        Toast.makeText(context, "Result = $result", Toast.LENGTH_SHORT).show()
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(onClick = { showPicker.value = true }) {
            Text(text = stringResource(R.string.app_name))
        }
        if (showPicker.value) {
            val config = Config(
                canChooseOnlyOneItem = false,
                uiConfig = UiConfig(
                    darkMode = DarkMode.DARK
                )
            )
            val intent = exFilePicker.createIntent(context, config)
            launcher.launch(intent)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ExFilePickerTheme {
        ScreenContent()
    }
}