package ru.bartwell.exfilepicker.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by BArtWell on 12.03.2017.
 */

public class ListUtils {

    public static <T> void copyListWithCondition(@NonNull T[] fromList, @NonNull List<T> toList, @Nullable ConditionChecker<T> checker) {
        copyListWithCondition(Arrays.asList(fromList), toList, checker);
    }

    public static <T> void copyListWithCondition(@NonNull List<T> fromList, @NonNull List<T> toList, @Nullable ConditionChecker<T> checker) {
        if (checker == null) {
            toList.addAll(fromList);
        } else {
            for (T item : fromList) {
                if (checker.check(item)) {
                    toList.add(item);
                }
            }
        }
    }

    public static <T> void filterList(@NonNull List<T> list, @NonNull ConditionChecker<T> checker) {
        Iterator<T> iterator = list.iterator();
        while (iterator.hasNext()) {
            if (checker.check(iterator.next())) {
                iterator.remove();
            }
        }
    }

    public interface ConditionChecker<T> {
        boolean check(@NonNull T item);
    }
}
