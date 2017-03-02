package ru.bartwell.exfilepicker.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import java.util.Locale;

import ru.bartwell.exfilepicker.R;

/**
 * Created by BArtWell on 22.11.2015.
 */
public class Utils {
    @NonNull
    public static String getFileExtension(@NonNull String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1) return "";
        return fileName.substring(index + 1, fileName.length()).toLowerCase(Locale.getDefault());
    }

    @NonNull
    public static String getHumanReadableFileSize(@NonNull Context context, long size) {
        String[] units = context.getResources().getStringArray(R.array.efp__size_units);
        for (int i = units.length - 1; i >= 0; i--) {
            if (size >= Math.pow(1024, i)) {
                return Math.round((size / Math.pow(1024, i))) + " " + units[i];
            }
        }
        return size + " " + units[0];
    }

    public static int attrToResId(@NonNull Context context, @AttrRes int attr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(new int[]{attr});
        return a.getResourceId(0, 0);
    }
}
