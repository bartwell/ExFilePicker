package ru.bartwell.exfilepickersample.items

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CheckBoxItem(modifier: Modifier = Modifier, title: String, onCheckedChange: (Boolean) -> Unit, isChecked: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp, 4.dp)
    ) {
        Checkbox(
            modifier = Modifier.padding(4.dp),
            checked = isChecked,
            onCheckedChange = onCheckedChange,
        )
        Text(
            modifier = modifier.align(Alignment.CenterVertically),
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun Preview() {
    Column {
        CheckBoxItem(
            isChecked = false,
            title = "Item",
            onCheckedChange = {}
        )
    }
}
