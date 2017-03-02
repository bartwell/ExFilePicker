package ru.bartwell.exfilepicker.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import ru.bartwell.exfilepicker.ExFilePicker;
import ru.bartwell.exfilepicker.R;

/**
 * Created by BArtWell on 01.03.2017.
 */
public class SortingDialog implements DialogInterface.OnClickListener {
    private final AlertDialog.Builder mAlert;
    private OnSortingSelectedListener mOnSortingSelectedListener;

    public SortingDialog(Context context) {
        mAlert = new AlertDialog.Builder(context);
        mAlert.setItems(context.getResources().getStringArray(R.array.efp__sorting_types), this);
    }

    public void setOnSortingSelectedListener(OnSortingSelectedListener listener) {
        mOnSortingSelectedListener = listener;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        ExFilePicker.SortingType sortingType = ExFilePicker.SortingType.NAME_ASC;
        switch (which) {
            case 1:
                sortingType = ExFilePicker.SortingType.NAME_DESC;
                break;
            case 2:
                sortingType = ExFilePicker.SortingType.SIZE_ASC;
                break;
            case 3:
                sortingType = ExFilePicker.SortingType.SIZE_DESC;
                break;
            case 4:
                sortingType = ExFilePicker.SortingType.DATE_ASC;
                break;
            case 5:
                sortingType = ExFilePicker.SortingType.DATE_DESC;
                break;
        }
        mOnSortingSelectedListener.onSortingSelected(sortingType);
    }

    public void show() {
        mAlert.show();
    }

    public interface OnSortingSelectedListener {
        void onSortingSelected(ExFilePicker.SortingType sortingType);
    }
}
