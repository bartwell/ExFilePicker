package ru.bartwell.exfilepicker.utils.comparator;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.Locale;

/**
 * Created by BArtWell on 28.02.2017.
 */
class FilesListNameAscComparator extends FilesListComparator {
    @Override
    @IntRange(from = -1, to = 1)
    int compareProperty(@NonNull File file1, @NonNull File file2) {
        return file1.getName().toLowerCase(Locale.getDefault()).compareTo(file2.getName().toLowerCase(Locale.getDefault()));
    }
}
