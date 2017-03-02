package ru.bartwell.exfilepicker.utils.comparator;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.Comparator;

/**
 * Created by BArtWell on 28.02.2017.
 */
abstract class FilesListComparator implements Comparator<File> {
    @Override
    @IntRange(from = -1, to = 1)
    public int compare(@NonNull File file1, @NonNull File file2) {
        if (file1.isDirectory() && !file2.isDirectory()) {
            return -1;
        } else if (!file1.isDirectory() && file2.isDirectory()) {
            return 1;
        } else {
            return compareProperty(file1, file2);
        }
    }

    @IntRange(from = -1, to = 1)
    abstract int compareProperty(@NonNull File file1, @NonNull File file2);
}
