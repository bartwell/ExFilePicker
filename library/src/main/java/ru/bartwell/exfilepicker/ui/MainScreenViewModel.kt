package ru.bartwell.exfilepicker.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Environment
import androidx.documentfile.provider.DocumentFile
import ru.bartwell.exfilepicker.ExFilePicker
import ru.bartwell.exfilepicker.core.BaseViewModel
import ru.bartwell.exfilepicker.core.extension.findActivity
import ru.bartwell.exfilepicker.core.extension.resolvePath
import ru.bartwell.exfilepicker.data.FileInfo
import ru.bartwell.exfilepicker.data.config.Config
import java.io.File

internal class MainScreenViewModel(
    private val context: Context,
    config: Config
) : BaseViewModel<MainScreenState>(MainScreenState(config = config)) {

    init {
        if (state.config.startDirectory == null) {
            val storagePath = Environment.getExternalStorageDirectory().absolutePath
            updateState { copy(currentDirectory = storagePath) }
        }
        readDir()
    }

    private fun readDir() {
        val files = DocumentFile.fromFile(File(state.currentDirectory))
            .listFiles()
            .mapNotNull { documentFile ->
                documentFile.resolvePath(context)?.let { path ->
                    val file = File(path)
                    FileInfo(
                        fileName = documentFile.name.orEmpty(),
                        parentDirectory = file.parent.orEmpty(),
                        absolutePath = file.absolutePath,
                        isDirectory = documentFile.isDirectory,
                        size = documentFile.length(),
                        lastModifiedMillis = documentFile.lastModified(),
                    )
                }
            }
        updateState { copy(files = files) }
    }

    fun onItemClick(position: Int) {
        context.findActivity()?.let { activity ->
            val result = arrayOf(state.files[position])
                .map { it.absolutePath }
                .toTypedArray()
            val intent = Intent()
            intent.putExtra(ExFilePicker.EXTRA_RESULT, result)
            activity.setResult(Activity.RESULT_OK, intent)
            activity.finish()
        }
    }
}

internal data class MainScreenState(
    val config: Config,
    val currentDirectory: String = config.startDirectory.orEmpty(),
    val files: List<FileInfo> = emptyList(),
)
