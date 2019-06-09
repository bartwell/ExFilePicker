package ru.bartwell.exfilepicker.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import java.util.LinkedHashMap;

import ru.bartwell.exfilepicker.R;
import ru.bartwell.exfilepicker.utils.Utils;

/**
 * Created by BArtWell on 09.06.2019.
 */
public class StorageDialog implements DialogInterface.OnClickListener {

    private final AlertDialog.Builder mAlert;
    private OnStorageSelectedListener mOnStorageSelectedListener;
    private LinkedHashMap<String, String> mStorages;

    public StorageDialog(Context context) {
        mAlert = new AlertDialog.Builder(context);
        mAlert.setTitle(R.string.efp__storage);
        mStorages = Utils.getExternalStoragePaths(context);
        mAlert.setItems(mStorages.values().toArray(new String[0]), this);
    }

    public void setOnStorageSelectedListener(OnStorageSelectedListener listener) {
        mOnStorageSelectedListener = listener;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        mOnStorageSelectedListener.onStorageSelected((String) mStorages.keySet().toArray()[which]);
    }

    public void show() {
        mAlert.show();
    }

    public interface OnStorageSelectedListener {
        void onStorageSelected(String path);
    }
}
