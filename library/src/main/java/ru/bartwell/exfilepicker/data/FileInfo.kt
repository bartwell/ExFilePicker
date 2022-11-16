package ru.bartwell.exfilepicker.data

data class FileInfo(
    val fileName: String,
    val parentDirectory: String,
    val absolutePath: String,
    val isDirectory: Boolean,
    val size: Long,
    val lastModifiedMillis: Long,
)
