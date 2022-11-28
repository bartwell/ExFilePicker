package ru.bartwell.exfilepickersample.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RadioButtonItem(modifier: Modifier = Modifier, title: String, onSelectedChange: () -> Unit, selected: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        RadioButton(
            modifier = Modifier.padding(4.dp),
            selected = selected,
            onClick =  onSelectedChange
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
        RadioButtonItem(
            selected = true,
            title = "Item",
            onSelectedChange = {  },
        )
    }
}
