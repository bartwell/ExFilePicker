package ru.bartwell.exfilepicker.ui.adapter.holder;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import java.io.File;

import ru.bartwell.exfilepicker.R;
import ru.bartwell.exfilepicker.ui.callback.OnListItemClickListener;
import ru.bartwell.exfilepicker.utils.Utils;
import ru.bartwell.exfilepicker.utils.thumbnail.ThumbnailCache;
import ru.bartwell.exfilepicker.utils.thumbnail.ThumbnailLoader;

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
        if (ThumbnailLoader.canHasThumbnail(file.getName())) {
            Bitmap bitmap = ThumbnailCache.getInstance().get(file.getAbsolutePath());
            if (bitmap == null) {
                new ThumbnailLoader(mContext, mThumbnail).execute(file);
            } else {
                mThumbnail.setImageBitmap(bitmap);
            }
        } else {
            mThumbnail.setImageResource(R.drawable.efp__ic_file);
        }
    }
}
