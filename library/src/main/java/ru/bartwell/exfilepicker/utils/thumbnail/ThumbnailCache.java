package ru.bartwell.exfilepicker.utils.thumbnail;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LruCache;

/**
 * Created by BArtWell on 22.11.2015.
 */
public class ThumbnailCache {

    @Nullable
    private static ThumbnailCache sThumbnailCache;
    @NonNull
    private LruCache<String, Bitmap> mCache;

    private ThumbnailCache() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return getBitmapSize(bitmap) / 1024;
            }
        };
    }

    @NonNull
    public static ThumbnailCache getInstance() {
        if (sThumbnailCache == null) {
            sThumbnailCache = new ThumbnailCache();
        }
        return sThumbnailCache;
    }

    public void add(@NonNull String key, @Nullable Bitmap bitmap) {
        if (bitmap != null && get(key) == null) {
            mCache.put(key, bitmap);
        }
    }

    @Nullable
    public Bitmap get(@Nullable String key) {
        if (key != null) {
            return mCache.get(key);
        }
        return null;
    }

    private int getBitmapSize(@NonNull Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
