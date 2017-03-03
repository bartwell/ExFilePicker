package ru.bartwell.exfilepicker.ui.callback;

/**
 * Created by BArtWell on 27.02.2017.
 */

public interface OnListItemClickListener {
    public static final int POSITION_UP = -1;
    void onListItemClick(int position);
    void onListItemLongClick(int position);
}
