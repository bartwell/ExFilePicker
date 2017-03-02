package ru.bartwell.exfilepicker.ui.adapter.holder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import java.io.File;

import ru.bartwell.exfilepicker.R;
import ru.bartwell.exfilepicker.ui.callback.OnListItemClickListener;

/**
 * Created by BArtWell on 26.02.2017.
 */
public class DirectoryFilesListHolder extends BaseFilesListHolder {
    @Nullable
    private final AppCompatTextView mFileSize;
    @NonNull
    private final AppCompatImageView mThumbnail;

    public DirectoryFilesListHolder(@NonNull View itemView) {
        super(itemView);
        mFileSize = (AppCompatTextView) this.itemView.findViewById(R.id.filesize);
        mThumbnail = (AppCompatImageView) this.itemView.findViewById(R.id.thumbnail);
    }

    @Override
    public void bind(@NonNull File file, boolean isMultiChoiceModeEnabled, boolean isSelected, @Nullable OnListItemClickListener listener) {
        super.bind(file, isMultiChoiceModeEnabled, isSelected, listener);
        if (mFileSize != null) {
            mFileSize.setVisibility(View.GONE);
        }
        mThumbnail.setImageResource(R.drawable.efp__ic_folder);
    }
}
