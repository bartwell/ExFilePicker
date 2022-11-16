package ru.bartwell.exfilepicker.data.config

@Suppress("ArrayInDataClass")
data class Config(
    val canChooseOnlyOneItem: Boolean = false,
    val showOnlyExtensions: Array<String> = emptyArray<String>(),
    val exceptExtensions: Array<String> = emptyArray<String>(),
    val isNewFolderButtonDisabled: Boolean = false,
    val isSortButtonDisabled: Boolean = false,
    val isQuitButtonEnabled: Boolean = false,
    val choiceType: ChoiceType = ChoiceType.ALL,
    val sortingType: SortingType = SortingType.NAME_ASC,
    val startDirectory: String? = null,
    val useFirstItemAsUpEnabled: Boolean = false,
    val hideHiddenFilesEnabled: Boolean = false,
    val uiConfig: UiConfig = UiConfig(),
)
