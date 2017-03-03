package ru.bartwell.exfilepicker.ui.adapter.holder;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;

import java.io.File;

import ru.bartwell.exfilepicker.R;
import ru.bartwell.exfilepicker.ui.callback.OnListItemClickListener;
import ru.bartwell.exfilepicker.utils.Utils;

/**
 * Created by BArtWell on 26.02.2017.
 */
public class FileFilesListHolder extends BaseFilesListHolder {
    @NonNull
    private final Context mContext;
    @Nullable
    private final AppCompatTextView mFileSize;
    @NonNull
    private final AppCompatImageView mThumbnail;

    public FileFilesListHolder(@NonNull View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mFileSize = (AppCompatTextView) itemView.findViewById(R.id.filesize);
        mThumbnail = (AppCompatImageView) itemView.findViewById(R.id.thumbnail);
    }

    @Override
    public void bind(@NonNull File file, boolean isMultiChoiceModeEnabled, boolean isSelected, @Nullable OnListItemClickListener listener) {
        super.bind(file, isMultiChoiceModeEnabled, isSelected, listener);
        if (mFileSize != null) {
            mFileSize.setVisibility(View.VISIBLE);
            mFileSize.setText(Utils.getHumanReadableFileSize(mContext, file.length()));
        }

        Glide.with(mContext)
                .load(file)
                .error(R.drawable.efp__ic_file)
                .into(mThumbnail);
    }
}
