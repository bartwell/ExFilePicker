package ru.bartwell.exfilepicker.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Locale;

import ru.bartwell.exfilepicker.R;

/**
 * Created by BArtWell on 22.11.2015.
 */
public class Utils {

    private final static String CACHE_DIR_PATH_PART = "/Android";

    @NonNull
    public static LinkedHashMap<String, String> getExternalStoragePaths(@NonNull Context context) {
        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        ArrayList<File> paths = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            paths.addAll(Arrays.asList(context.getExternalCacheDirs()));
        } else {
            paths.add(context.getExternalCacheDir());
        }
        for (File dir : paths) {
            String path = dir.getPath().split(CACHE_DIR_PATH_PART)[0];
            result.put(path, new File(path).getName());
        }
        return result;
    }

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
