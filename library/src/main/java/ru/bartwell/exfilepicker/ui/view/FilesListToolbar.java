package ru.bartwell.exfilepicker.ui.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;

import ru.bartwell.exfilepicker.R;
import ru.bartwell.exfilepicker.utils.Utils;

/**
 * Created by BArtWell on 27.02.2017.
 */

public class FilesListToolbar extends Toolbar {
    private boolean mQuitButtonEnabled;
    private CharSequence mTitle;

    public FilesListToolbar(@NonNull Context context) {
        super(context);
    }

    public FilesListToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FilesListToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setQuitButtonEnabled(boolean enabled) {
        mQuitButtonEnabled = enabled;
    }

    public void setMultiChoiceModeEnabled(boolean enabled) {
        getMenu().clear();
        if (enabled) {
            inflateMenu(R.menu.files_list_multi_choice);
            mTitle = getTitle();
            setTitle(null);
            setNavigationIcon(Utils.attrToResId(getContext(), R.attr.efp__ic_action_cancel));
        } else {
            inflateMenu(R.menu.files_list_single_choice);
            if (!TextUtils.isEmpty(mTitle)) {
                setTitle(mTitle);
            }
            if (mQuitButtonEnabled) {
                setNavigationIcon(Utils.attrToResId(getContext(), R.attr.efp__ic_action_cancel));
            } else {
                setNavigationIcon(null);
            }
        }
    }
}
