package ru.bartwell.exfilepicker.core.extension

import android.content.Context
import android.provider.MediaStore
import androidx.documentfile.provider.DocumentFile

fun DocumentFile.resolvePath(context: Context): String? {
    val result: String?
    val cursor = context.contentResolver.query(uri, null, null, null, null)
    if (cursor == null) {
        result = uri.path
    } else {
        cursor.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        result = cursor.getString(idx)
        cursor.close()
    }
    return result
}
