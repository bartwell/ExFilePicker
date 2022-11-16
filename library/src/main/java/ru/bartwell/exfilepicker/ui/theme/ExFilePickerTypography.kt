package ru.bartwell.exfilepicker.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal val Typography: Typography = Typography(
    titleMedium = ExFilePickerTypography.toolbarTitle,
    bodyMedium = ExFilePickerTypography.itemTitle,
    bodySmall = ExFilePickerTypography.itemSubtitle,
)

internal object ExFilePickerTypography {
    private val defaultFontFamily: FontFamily = FontFamily.Default
    val itemTitle: TextStyle = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        fontSize = 16.sp,
    )
    val itemSubtitle: TextStyle = itemTitle.copy(
        fontSize = 14.sp,
    )
    val toolbarTitle: TextStyle = TextStyle(
        fontFamily = defaultFontFamily,
        fontWeight = FontWeight.Bold,
        lineHeight = 24.sp,
        fontSize = 18.sp,
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Column(modifier = Modifier.padding(8.dp)) {
        Text("bodyMedium", style = ExFilePickerTypography.itemTitle)
        Text("bodySmall", style = ExFilePickerTypography.itemSubtitle)
        Text("titleMedium", style = ExFilePickerTypography.toolbarTitle)
    }
}
