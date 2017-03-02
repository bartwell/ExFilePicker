package ru.bartwell.exfilepicker.utils.thumbnail;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Arrays;

import ru.bartwell.exfilepicker.R;
import ru.bartwell.exfilepicker.utils.Utils;

/**
 * Created by BArtWell on 22.11.2015.
 */
public class ThumbnailLoader extends AsyncTask<File, Void, Bitmap> {

    private static final String TAG = "ThumbnailLoader";

    private static final String[] VIDEO_EXTENSIONS = {"avi", "mp4", "3gp", "mov"};
    private static final String[] IMAGES_EXTENSIONS = {"jpeg", "jpg", "png", "gif", "bmp"};

    @NonNull
    private final Context mContext;
    @NonNull
    private final WeakReference<ImageView> mImageViewReference;

    public ThumbnailLoader(@NonNull Context context, @NonNull ImageView imageView) {
        mContext = context;
        mImageViewReference = new WeakReference<>(imageView);
    }

    @TargetApi(Build.VERSION_CODES.ECLAIR)
    @Override
    protected Bitmap doInBackground(@NonNull File... arg0) {
        Bitmap bitmap = null;
        File file = arg0[0];
        Log.d(TAG, "Loading thumbnail");
        if (file != null) {
            Log.d(TAG, file.getAbsolutePath());
            try {
                ContentResolver contentResolver = mContext.getContentResolver();
                if (Arrays.asList(VIDEO_EXTENSIONS).contains(Utils.getFileExtension(file.getName()))) {
                    Log.d(TAG, "Video");
                    Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Video.Media._ID}, MediaStore.Video.Media.DATA + "='" + file.getAbsolutePath() + "'", null, null);
                    if (cursor != null) {
                        Log.d(TAG, "Cursor is not null");
                        if (cursor.getCount() > 0) {
                            Log.d(TAG, "Cursor has data");
                            cursor.moveToFirst();
                            bitmap = MediaStore.Video.Thumbnails.getThumbnail(contentResolver, cursor.getInt(0), MediaStore.Video.Thumbnails.MICRO_KIND, null);
                        }
                        cursor.close();
                    }
                } else if (Arrays.asList(IMAGES_EXTENSIONS).contains(Utils.getFileExtension(file.getName()))) {
                    Log.d(TAG, "Image");
                    Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "='" + file.getAbsolutePath() + "'", null, null);
                    if (cursor != null) {
                        Log.d(TAG, "Cursor is not null");
                        if (cursor.getCount() > 0) {
                            Log.d(TAG, "Cursor has data");
                            cursor.moveToFirst();
                            bitmap = MediaStore.Images.Thumbnails.getThumbnail(contentResolver, cursor.getInt(0), MediaStore.Images.Thumbnails.MINI_KIND, null);
                        }
                        cursor.close();
                    }
                }
                Log.d(TAG, "Finished");
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "Thumbnail: " + (bitmap == null ? "null" : "Ok"));
        if (bitmap != null)
            ThumbnailCache.getInstance().add(file.getAbsolutePath(), bitmap);
        return bitmap;
    }

    @Override
    protected void onPostExecute(@Nullable Bitmap bitmap) {
        final ImageView imageView = mImageViewReference.get();
        if (imageView != null) {
            if (bitmap == null) {
                imageView.setImageResource(R.drawable.efp__ic_file);
            } else {
                imageView.setImageBitmap(bitmap);
            }
        }
    }

    public static boolean canHasThumbnail(@NonNull String fileName) {
        return Arrays.asList(VIDEO_EXTENSIONS).contains(Utils.getFileExtension(fileName)) || Arrays.asList(IMAGES_EXTENSIONS).contains(Utils.getFileExtension(fileName));
    }

}