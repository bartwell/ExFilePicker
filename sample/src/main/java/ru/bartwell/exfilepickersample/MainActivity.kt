package ru.bartwell.exfilepickersample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.bartwell.exfilepicker.ExFilePicker
import ru.bartwell.exfilepicker.data.config.*
import ru.bartwell.exfilepickersample.items.CheckBoxItem
import ru.bartwell.exfilepickersample.items.RadioButtonItem
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
    val radioOptions = listOf(
        stringResource(R.string.choice_type_all),
        stringResource(R.string.choice_type_files),
        stringResource(R.string.choice_type_directories)
    )
    val scrollState = rememberScrollState()
    val onlyOneCheckboxState = remember { mutableStateOf(false) }
    val filterListedCheckboxState = remember { mutableStateOf(false) }
    val filterExcludeCheckboxState = remember { mutableStateOf(false) }
    val newFolderCheckboxState = remember { mutableStateOf(false) }
    val disableSortCheckboxState = remember { mutableStateOf(false) }
    val enableQuitCheckboxState = remember { mutableStateOf(false) }
    val reverseSortingCheckboxState = remember { mutableStateOf(false) }
    val startFromRootCheckboxState = remember { mutableStateOf(false) }
    val firstItemAsUpCheckboxState = remember { mutableStateOf(false) }
    val hideHiddenFilesCheckboxState = remember { mutableStateOf(false) }
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val showPickerState = remember { mutableStateOf(false) }

    val context = LocalContext.current
    val exFilePicker = ExFilePicker()
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        showPickerState.value = false
        val result = exFilePicker.getResult(it.data)?.toList().toString()
        Toast.makeText(context, "Result = $result", Toast.LENGTH_SHORT).show()
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState),
    ) {
        CheckBoxItem(
            title = stringResource(R.string.only_one_item), onCheckedChange = { onlyOneCheckboxState.value = it },
            isChecked = onlyOneCheckboxState.value
        )
        CheckBoxItem(
            title = stringResource(R.string.filter_listed), onCheckedChange = { filterListedCheckboxState.value = it },
            isChecked = filterListedCheckboxState.value
        )
        CheckBoxItem(
            title = stringResource(R.string.filter_exclude), onCheckedChange = { filterExcludeCheckboxState.value = it },
            isChecked = filterExcludeCheckboxState.value
        )
        CheckBoxItem(
            title = stringResource(R.string.disable_new_folder_button), onCheckedChange = { newFolderCheckboxState.value = it },
            isChecked = newFolderCheckboxState.value
        )
        CheckBoxItem(
            title = stringResource(R.string.disable_sort_button), onCheckedChange = { disableSortCheckboxState.value = it },
            isChecked = disableSortCheckboxState.value
        )
        CheckBoxItem(
            title = stringResource(R.string.enable_quit_button), onCheckedChange = { enableQuitCheckboxState.value = it },
            isChecked = enableQuitCheckboxState.value
        )
        CheckBoxItem(
            title = stringResource(R.string.reverse_sorting), onCheckedChange = { reverseSortingCheckboxState.value = it },
            isChecked = reverseSortingCheckboxState.value
        )
        CheckBoxItem(
            title = stringResource(R.string.start_from_root), onCheckedChange = { startFromRootCheckboxState.value = it },
            isChecked = startFromRootCheckboxState.value
        )
        CheckBoxItem(
            title = stringResource(R.string.first_item_as_up), onCheckedChange = { firstItemAsUpCheckboxState.value = it },
            isChecked = firstItemAsUpCheckboxState.value
        )
        CheckBoxItem(
            title = stringResource(R.string.hide_hidden_files), onCheckedChange = { hideHiddenFilesCheckboxState.value = it },
            isChecked = hideHiddenFilesCheckboxState.value
        )

        Text(
            text = stringResource(R.string.choice_type),
            modifier = Modifier.padding(start = 20.dp, top = 0.dp, end = 0.dp, bottom = 20.dp)
        )
        radioOptions.forEach { text ->
            RadioButtonItem(
                selected = (text == selectedOption),
                title = text,
                onSelectedChange = {
                    onOptionSelected(text)
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Button(onClick = { showPickerState.value = true }) {
                Text(text = stringResource(R.string.choose_files))
            }
            if (showPickerState.value) {
                val onlyExtensions = if (filterListedCheckboxState.value) {
                    arrayOf("*.jpeg", "*.jpg")
                } else {
                    emptyArray()
                }
                val exceptExtensions = if (filterExcludeCheckboxState.value) {
                    arrayOf("*.jpg")
                } else {
                    emptyArray()
                }
                val sortingType = if (reverseSortingCheckboxState.value) {
                    SortingType.NAME_DESC
                } else {
                    SortingType.NAME_ASC
                }
                val startDirectory = if (startFromRootCheckboxState.value) {
                    "/"
                } else {
                    null
                }
                val choiceType = when (selectedOption) {
                    stringResource(R.string.choice_type_all) -> {
                        ChoiceType.ALL
                    }
                    stringResource(R.string.choice_type_files) -> {
                        ChoiceType.FILES
                    }
                    else -> {
                        ChoiceType.DIRECTORIES
                    }
                }
                val config = Config(
                    canChooseOnlyOneItem = onlyOneCheckboxState.value,
                    showOnlyExtensions = onlyExtensions,
                    exceptExtensions = exceptExtensions,
                    isNewFolderButtonDisabled = newFolderCheckboxState.value,
                    isSortButtonDisabled = disableSortCheckboxState.value,
                    isQuitButtonEnabled = enableQuitCheckboxState.value,
                    sortingType = sortingType,
                    startDirectory = startDirectory,
                    useFirstItemAsUpEnabled = firstItemAsUpCheckboxState.value,
                    hideHiddenFilesEnabled = hideHiddenFilesCheckboxState.value,
                    choiceType = choiceType,
                    uiConfig = UiConfig(
                        darkMode = DarkMode.DARK
                    )
                )
                val intent = exFilePicker.createIntent(context, config)
                launcher.launch(intent)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ExFilePickerTheme {
        ScreenContent()
    }
}
