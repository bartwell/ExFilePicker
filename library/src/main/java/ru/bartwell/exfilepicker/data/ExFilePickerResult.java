package ru.bartwell.exfilepicker.data;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import ru.bartwell.exfilepicker.ExFilePicker;

/**
 * Created by BArtWell on 26.02.2017.
 */

public class ExFilePickerResult implements Parcelable {
    @NonNull
    private String mPath = "";
    @NonNull
    private List<String> mNames = new ArrayList<>();
    private int mCount = 0;

    private ExFilePickerResult(Parcel in) {
        mPath = in.readString();
        mNames = in.createStringArrayList();
        mCount = in.readInt();
    }

    public ExFilePickerResult(@NonNull String path, @NonNull List<String> names) {
        mPath = path;
        mNames = names;
        mCount = names.size();
    }

    @NonNull
    public String getPath() {
        return mPath;
    }

    @NonNull
    public List<String> getNames() {
        return mNames;
    }

    public int getCount() {
        return mCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mPath);
        parcel.writeStringList(mNames);
        parcel.writeInt(mCount);
    }

    public static final Creator<ExFilePickerResult> CREATOR = new Creator<ExFilePickerResult>() {
        @Override
        public ExFilePickerResult createFromParcel(Parcel in) {
            return new ExFilePickerResult(in);
        }

        @Override
        public ExFilePickerResult[] newArray(int size) {
            return new ExFilePickerResult[size];
        }
    };

    @Nullable
    public static ExFilePickerResult getFromIntent(@Nullable Intent intent) {
        if (intent != null) {
            return intent.getParcelableExtra(ExFilePicker.EXTRA_RESULT);
        }
        return null;
    }
}
