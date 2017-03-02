package ru.bartwell.exfilepicker.utils.comparator;

import android.support.annotation.NonNull;

import java.io.File;
import java.util.Comparator;

import ru.bartwell.exfilepicker.ExFilePicker;

/**
 * Created by BArtWell on 28.02.2017.
 */

public class FilesListComparatorHelper {
    @NonNull
    public static FilesListComparator getComparator(ExFilePicker.SortingType sortingType) {
        switch (sortingType) {
            case NAME_DESC:
                return new FilesListNameDescComparator();
            case SIZE_ASC:
                return new FilesListSizeAscComparator();
            case SIZE_DESC:
                return new FilesListSizeDescComparator();
            case DATE_ASC:
                return new FilesListDateAscComparator();
            case DATE_DESC:
                return new FilesListDateDescComparator();
            default:
                return new FilesListNameAscComparator();
        }
    }
}
