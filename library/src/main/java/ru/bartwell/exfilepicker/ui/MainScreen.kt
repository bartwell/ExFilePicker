package ru.bartwell.exfilepicker.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.bartwell.exfilepicker.data.FileInfo
import ru.bartwell.exfilepicker.data.config.Config
import ru.bartwell.exfilepicker.ui.theme.ExFilePickerTheme
import ru.bartwell.exfilepicker.ui.theme.ExFilePickerTypography
import ru.bartwell.exfilepicker.ui.theme.LocalColors

@Composable
internal fun MainScreen(exFilePickerConfig: Config) {
    val viewModel = MainScreenViewModel(LocalContext.current, exFilePickerConfig)
    val state by viewModel.stateFlow.collectAsState()

    MainScreenContent(
        state = state,
        viewModel::onItemClick,
    )
}

@Composable
private fun MainScreenContent(
    state: MainScreenState,
    onItemClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Toolbar here")
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(state.files.size) { position ->
                Item(state.files[position]) {
                    onItemClick(position)
                }
            }
        }
    }
}

@Composable
fun Item(
    fileInfo: FileInfo,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = fileInfo.fileName,
            color = LocalColors.current.itemTitle,
            style = ExFilePickerTypography.itemTitle
        )
        Text(
            text = fileInfo.size.toString(),
            color = LocalColors.current.itemSubtitle,
            style = ExFilePickerTypography.itemSubtitle
        )
    }
}

@Suppress("MagicNumber")
@Preview(showBackground = true)
@Composable
private fun Preview() {
    val files = listOf(
        FileInfo("folder", "", "", true, 0, 0),
        FileInfo("file.txt", "", "", false, 100_500, 0)
    )
    val config = Config()
    ExFilePickerTheme(config.uiConfig) {
        Surface {
            MainScreenContent(
                state = MainScreenState(config = config, files = files),
                onItemClick = {},
            )
        }
    }
}
