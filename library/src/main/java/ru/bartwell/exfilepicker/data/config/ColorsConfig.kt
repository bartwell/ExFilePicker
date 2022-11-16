package ru.bartwell.exfilepicker.data.config

import android.graphics.Color

data class ColorsConfig(
    val toolbarBackground: Int,
    val toolbarTitle: Int,
    val background: Int,
    val itemTitle: Int,
    val itemSubtitle: Int,
    val checkboxChecked: Int,
    val checkboxUnchecked: Int,
    val statusBar: Int,
    val navigationBar: Int,
) {
    companion object {
        val DARK = ColorsConfig(
            toolbarBackground = Color.BLACK,
            toolbarTitle = Color.WHITE,
            background = Color.BLACK,
            itemTitle = Color.WHITE,
            itemSubtitle = Color.WHITE,
            checkboxChecked = Color.WHITE,
            checkboxUnchecked = Color.DKGRAY,
            statusBar = Color.BLACK,
            navigationBar = Color.BLACK,
        )
        val LIGHT = ColorsConfig(
            toolbarBackground = Color.GRAY,
            toolbarTitle = Color.BLACK,
            background = Color.WHITE,
            itemTitle = Color.BLACK,
            itemSubtitle = Color.DKGRAY,
            checkboxChecked = Color.BLACK,
            checkboxUnchecked = Color.GRAY,
            statusBar = Color.GRAY,
            navigationBar = Color.GRAY,
        )
    }
}
