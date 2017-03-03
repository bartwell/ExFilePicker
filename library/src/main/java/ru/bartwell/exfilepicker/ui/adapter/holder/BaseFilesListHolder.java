package ru.bartwell.exfilepicker.ui.adapter.holder;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.File;

import ru.bartwell.exfilepicker.R;
import ru.bartwell.exfilepicker.ui.callback.OnListItemClickListener;

/**
 * Created by BArtWell on 26.02.2017.
 */
public abstract class BaseFilesListHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
    @NonNull
    private final AppCompatTextView mFileName;
    @NonNull
    private final AppCompatCheckBox mCheckBox;
    @Nullable
    private OnListItemClickListener mListener;

    BaseFilesListHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        mFileName = (AppCompatTextView) itemView.findViewById(R.id.filename);
        mCheckBox = (AppCompatCheckBox) itemView.findViewById(R.id.checkbox);
    }

    public void bind(@NonNull File file, boolean isMultiChoiceModeEnabled, boolean isSelected, @Nullable OnListItemClickListener listener) {
        setOnListItemClickListener(listener);
        mFileName.setText(file.getName());
        mCheckBox.setVisibility(isMultiChoiceModeEnabled ? View.VISIBLE : View.GONE);
        mCheckBox.setChecked(isSelected);
    }

    void setOnListItemClickListener(@Nullable OnListItemClickListener listener) {
        mListener = listener;
    }

    int getItemPosition() {
        return getAdapterPosition();
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onListItemClick(getItemPosition());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if (mListener != null) {
            mListener.onListItemLongClick(getItemPosition());
        }
        return true;
    }
}
