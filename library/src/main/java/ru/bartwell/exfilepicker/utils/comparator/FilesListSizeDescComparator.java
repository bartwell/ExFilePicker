package ru.bartwell.exfilepicker.utils.comparator;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.io.File;

/**
 * Created by BArtWell on 28.02.2017.
 */
class FilesListSizeDescComparator extends FilesListComparator {
    @Override
    @IntRange(from = -1, to = 1)
    int compareProperty(@NonNull File file1, @NonNull File file2) {
        return Long.valueOf(file2.length()).compareTo(file1.length());
    }
}
