package ru.bartwell.exfilepicker.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.TextView;

import ru.bartwell.exfilepicker.R;

/**
 * Created by BArtWell on 01.03.2017.
 */
public class NewFolderDialog implements DialogInterface.OnClickListener {
    @Nullable
    private OnNewFolderNameEnteredListener mListener;
    private AlertDialog.Builder mAlert;

    public NewFolderDialog(@NonNull Context context) {
        mAlert = new AlertDialog.Builder(context);
        mAlert.setTitle(R.string.efp__new_folder);
        mAlert.setView(LayoutInflater.from(context).inflate(R.layout.efp__new_folder, null));
        mAlert.setPositiveButton(android.R.string.ok, this);
        mAlert.setNegativeButton(android.R.string.cancel, null);
    }

    public void setOnNewFolderNameEnteredListener(OnNewFolderNameEnteredListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        TextView name = (TextView) ((AlertDialog) dialogInterface).findViewById(R.id.name);
        if (mListener != null && name != null) {
            mListener.onNewFolderNameEntered(name.getText().toString());
        }
    }

    public void show() {
        mAlert.show();
    }

    public interface OnNewFolderNameEnteredListener {
        void onNewFolderNameEntered(@NonNull String name);
    }
}
