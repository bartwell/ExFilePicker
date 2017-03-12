package ru.bartwell.exfilepicker;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.bartwell.exfilepicker.ui.activity.ExFilePickerActivity;

/**
 * Created by BArtWell on 26.02.2017.
 */

public class ExFilePicker {

    public static final String EXTRA_RESULT = "RESULT";

    private boolean mCanChooseOnlyOneItem;
    @Nullable
    private String[] mShowOnlyExtensions;
    @Nullable
    private String[] mExceptExtensions;
    private boolean mIsNewFolderButtonDisabled;
    private boolean mIsSortButtonDisabled;
    private boolean mIsQuitButtonEnabled;
    @NonNull
    private ChoiceType mChoiceType = ChoiceType.ALL;
    @NonNull
    private SortingType mSortingType = SortingType.NAME_ASC;
    @Nullable
    private String mStartDirectory;
    private boolean mUseFirstItemAsUpEnabled;
    private boolean mHideHiddenFilesEnabled;

    public void setCanChooseOnlyOneItem(boolean canChooseOnlyOneItem) {
        mCanChooseOnlyOneItem = canChooseOnlyOneItem;
    }

    public void setShowOnlyExtensions(@Nullable String... extension) {
        mShowOnlyExtensions = extension;
    }

    public void setExceptExtensions(@Nullable String... extension) {
        mExceptExtensions = extension;
    }

    public void setNewFolderButtonDisabled(boolean disabled) {
        mIsNewFolderButtonDisabled = disabled;
    }

    public void setSortButtonDisabled(boolean disabled) {
        mIsSortButtonDisabled = disabled;
    }

    public void setQuitButtonEnabled(boolean enabled) {
        mIsQuitButtonEnabled = enabled;
    }

    public void setChoiceType(@NonNull ChoiceType choiceType) {
        mChoiceType = choiceType;
    }

    public void setSortingType(@NonNull SortingType sortingType) {
        mSortingType = sortingType;
    }

    public void setStartDirectory(@Nullable String startDirectory) {
        mStartDirectory = startDirectory;
    }

    public void setUseFirstItemAsUpEnabled(boolean enabled) {
        mUseFirstItemAsUpEnabled = enabled;
    }

    public void setHideHiddenFilesEnabled(boolean enabled) {
        mHideHiddenFilesEnabled = enabled;
    }

    public void start(@NonNull Activity activity, int requestCode) {
        Intent intent = new Intent(activity, ExFilePickerActivity.class);
        intent.putExtra(ExFilePickerActivity.EXTRA_CAN_CHOOSE_ONLY_ONE_ITEM, mCanChooseOnlyOneItem);
        intent.putExtra(ExFilePickerActivity.EXTRA_SHOW_ONLY_EXTENSIONS, mShowOnlyExtensions);
        intent.putExtra(ExFilePickerActivity.EXTRA_EXCEPT_EXTENSIONS, mExceptExtensions);
        intent.putExtra(ExFilePickerActivity.EXTRA_IS_NEW_FOLDER_BUTTON_DISABLED, mIsNewFolderButtonDisabled);
        intent.putExtra(ExFilePickerActivity.EXTRA_IS_SORT_BUTTON_DISABLED, mIsSortButtonDisabled);
        intent.putExtra(ExFilePickerActivity.EXTRA_IS_QUIT_BUTTON_ENABLED, mIsQuitButtonEnabled);
        intent.putExtra(ExFilePickerActivity.EXTRA_CHOICE_TYPE, mChoiceType);
        intent.putExtra(ExFilePickerActivity.EXTRA_SORTING_TYPE, mSortingType);
        intent.putExtra(ExFilePickerActivity.EXTRA_START_DIRECTORY, mStartDirectory);
        intent.putExtra(ExFilePickerActivity.EXTRA_USE_FIRST_ITEM_AS_UP_ENABLED, mUseFirstItemAsUpEnabled);
        intent.putExtra(ExFilePickerActivity.EXTRA_HIDE_HIDDEN_FILES, mHideHiddenFilesEnabled);
        activity.startActivityForResult(intent, requestCode);
    }

    public enum ChoiceType {
        ALL, FILES, DIRECTORIES
    }

    public enum SortingType {
        NAME_ASC, NAME_DESC, SIZE_ASC, SIZE_DESC, DATE_ASC, DATE_DESC
    }
}
