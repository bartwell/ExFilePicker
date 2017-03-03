package ru.bartwell.exfilepicker.ui.adapter.holder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import java.io.File;

import ru.bartwell.exfilepicker.R;
import ru.bartwell.exfilepicker.ui.callback.OnListItemClickListener;

/**
 * Created by BArtWell on 03.03.2017.
 */

public class UpFilesListHolder extends BaseFilesListHolder {
    @NonNull
    private final AppCompatTextView mFileName;
    @NonNull
    private final AppCompatCheckBox mCheckBox;
    @Nullable
    private final AppCompatTextView mFileSize;
    @NonNull
    private final AppCompatImageView mThumbnail;

    public UpFilesListHolder(@NonNull View itemView) {
        super(itemView);
        mFileName = (AppCompatTextView) itemView.findViewById(R.id.filename);
        mCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.checkbox);
        mFileSize = (AppCompatTextView) itemView.findViewById(R.id.filesize);
        mThumbnail = (AppCompatImageView) itemView.findViewById(R.id.thumbnail);
    }

    public void bind(@Nullable OnListItemClickListener listener) {
        setOnListItemClickListener(listener);
        mFileName.setText("..");
        mCheckBox.setVisibility(View.GONE);
        if (mFileSize != null) {
            mFileSize.setVisibility(View.GONE);
        }
        mThumbnail.setImageResource(R.drawable.efp__ic_up);
    }

    @Override
    int getItemPosition() {
        return OnListItemClickListener.POSITION_UP;
    }
}
