package ru.bartwell.exfilepicker.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import ru.bartwell.exfilepicker.ExFilePicker;
import ru.bartwell.exfilepicker.R;
import ru.bartwell.exfilepicker.data.ExFilePickerResult;
import ru.bartwell.exfilepicker.ui.adapter.FilesListAdapter;
import ru.bartwell.exfilepicker.ui.callback.OnListItemClickListener;
import ru.bartwell.exfilepicker.ui.dialog.NewFolderDialog;
import ru.bartwell.exfilepicker.ui.dialog.SortingDialog;
import ru.bartwell.exfilepicker.ui.view.FilesListToolbar;
import ru.bartwell.exfilepicker.utils.Utils;

public class ExFilePickerActivity extends AppCompatActivity implements OnListItemClickListener,
        Toolbar.OnMenuItemClickListener, View.OnClickListener, NewFolderDialog.OnNewFolderNameEnteredListener,
        SortingDialog.OnSortingSelectedListener {

    public static final String EXTRA_CAN_CHOOSE_ONLY_ONE_ITEM = "CAN_CHOOSE_ONLY_ONE_ITEM";
    public static final String EXTRA_SHOW_ONLY_EXTENSIONS = "SHOW_ONLY_EXTENSIONS";
    public static final String EXTRA_EXCEPT_EXTENSIONS = "EXCEPT_EXTENSIONS";
    public static final String EXTRA_IS_NEW_FOLDER_BUTTON_DISABLED = "IS_NEW_FOLDER_BUTTON_DISABLED";
    public static final String EXTRA_IS_SORT_BUTTON_DISABLED = "IS_SORT_BUTTON_DISABLED";
    public static final String EXTRA_IS_QUIT_BUTTON_ENABLED = "IS_QUIT_BUTTON_ENABLED";
    public static final String EXTRA_CHOICE_TYPE = "CHOICE_TYPE";
    public static final String EXTRA_SORTING_TYPE = "SORTING_TYPE";
    public static final String EXTRA_START_DIRECTORY = "START_DIRECTORY";
    public static final String PERMISSION_READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 1;
    private static final int REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 2;
    private static final String TOP_DIRECTORY = "/";

    private boolean mCanChooseOnlyOneItem;
    @Nullable
    private String[] mShowOnlyExtensions;
    @Nullable
    private String[] mExceptExtensions;
    private boolean mIsNewFolderButtonDisabled;
    private boolean mIsSortButtonDisabled;
    private boolean mIsQuitButtonEnabled;
    @NonNull
    private ExFilePicker.ChoiceType mChoiceType = ExFilePicker.ChoiceType.ALL;
    @NonNull
    private ExFilePicker.SortingType mSortingType = ExFilePicker.SortingType.NAME_ASC;
    private File mCurrentDirectory;
    private FilesListToolbar mToolbar;
    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private FilesListAdapter mAdapter;
    private boolean mIsMultiChoiceModeEnabled;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_file_picker);

        handleIntent();
        setupViews();

        if (ContextCompat.checkSelfPermission(this, PERMISSION_READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            readDirectory(mCurrentDirectory);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{PERMISSION_READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_READ_EXTERNAL_STORAGE:
                readDirectory(mCurrentDirectory);
                break;
            case REQUEST_CODE_WRITE_EXTERNAL_STORAGE:
                showNewFolderDialog();
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onListItemClick(int position) {
        if (mIsMultiChoiceModeEnabled) {
            if (mCanChooseOnlyOneItem) {
                mAdapter.deselect();
            }
            mAdapter.setItemSelected(position, !mAdapter.isItemSelected(position));
        } else {
            File item = mAdapter.getItem(position);
            if (item.isDirectory()) {
                mCurrentDirectory = new File(mCurrentDirectory, item.getName());
                readDirectory(mCurrentDirectory);
            } else {
                finishWithResult(mCurrentDirectory, item.getName());
            }
        }
    }

    @Override
    public void onListItemLongClick(int position) {
        if (!mIsMultiChoiceModeEnabled) {
            mIsMultiChoiceModeEnabled = true;
            setMultiChoiceModeEnabled(true);
            mAdapter.setItemSelected(position, true);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.ok) {
            if (mIsMultiChoiceModeEnabled) {
                finishWithResult(mCurrentDirectory, mAdapter.getSelectedItems());
            } else if (mChoiceType == ExFilePicker.ChoiceType.DIRECTORIES) {
                if (mCurrentDirectory.getAbsolutePath().equals(TOP_DIRECTORY)) {
                    finishWithResult(mCurrentDirectory, "/");
                } else {
                    finishWithResult(mCurrentDirectory.getParentFile(), mCurrentDirectory.getName());
                }
            }
        } else if (itemId == R.id.sort) {
            SortingDialog dialog = new SortingDialog(this);
            dialog.setOnSortingSelectedListener(this);
            dialog.show();
        } else if (itemId == R.id.new_folder) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                showNewFolderDialog();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
            }
        } else if (itemId == R.id.select_all) {
            mAdapter.selectAll();
        } else if (itemId == R.id.deselect) {
            mAdapter.deselect();
        } else if (itemId == R.id.invert_selection) {
            mAdapter.invertSelection();
        } else if (itemId == R.id.change_view) {
            toggleViewMode();
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        if (mIsMultiChoiceModeEnabled) {
            setMultiChoiceModeEnabled(false);
        } else {
            finish();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (event.getAction() == KeyEvent.ACTION_UP) {
                if (mIsMultiChoiceModeEnabled) {
                    setMultiChoiceModeEnabled(false);
                } else {
                    if (mCurrentDirectory.getAbsolutePath().equals(TOP_DIRECTORY)) {
                        finish();
                    } else {
                        mCurrentDirectory = mCurrentDirectory.getParentFile();
                        readDirectory(mCurrentDirectory);
                    }
                }
            } else if (event.getAction() == KeyEvent.ACTION_DOWN && (event.getFlags() & KeyEvent.FLAG_LONG_PRESS) == KeyEvent.FLAG_LONG_PRESS) {
                finish();
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onNewFolderNameEntered(@NonNull String name) {
        if (name.length() > 0) {
            File file = new File(mCurrentDirectory, name);
            if (file.exists()) {
                Toast.makeText(ExFilePickerActivity.this, R.string.efp__folder_already_exists, Toast.LENGTH_SHORT).show();
            } else {
                if (file.mkdir()) {
                    readDirectory(mCurrentDirectory);
                    Toast.makeText(ExFilePickerActivity.this, R.string.efp__folder_created, Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ExFilePickerActivity.this, R.string.efp__folder_not_created, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onSortingSelected(ExFilePicker.SortingType sortingType) {
        mSortingType = sortingType;
        mAdapter.sort(mSortingType);
    }


    private void readDirectory(File directory) {
        setTitle(directory);
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
            List<File> list = new ArrayList<>();
            if (mShowOnlyExtensions != null && mShowOnlyExtensions.length > 0) {
                for (File file : files) {
                    if (file.isDirectory() || Arrays.asList(mShowOnlyExtensions).contains(Utils.getFileExtension(file.getName()))) {
                        list.add(file);
                    }
                }
            } else {
                Collections.addAll(list, files);
            }
            if (mExceptExtensions != null && mExceptExtensions.length > 0) {
                Iterator<File> iterator = list.iterator();
                while (iterator.hasNext()) {
                    File file = iterator.next();
                    if (!file.isDirectory() && Arrays.asList(mExceptExtensions).contains(Utils.getFileExtension(file.getName()))) {
                        iterator.remove();
                    }
                }
            }
            mAdapter.setItems(list, mSortingType);
        }
    }

    private void setTitle(File directory) {
        if (directory.getAbsolutePath().equals(TOP_DIRECTORY)) {
            mToolbar.setTitle(TOP_DIRECTORY);
        } else {
            mToolbar.setTitle(directory.getName());
        }
    }

    private void handleIntent() {
        Intent intent = getIntent();
        mCanChooseOnlyOneItem = intent.getBooleanExtra(EXTRA_CAN_CHOOSE_ONLY_ONE_ITEM, false);
        mShowOnlyExtensions = intent.getStringArrayExtra(EXTRA_SHOW_ONLY_EXTENSIONS);
        mExceptExtensions = intent.getStringArrayExtra(EXTRA_EXCEPT_EXTENSIONS);
        mIsNewFolderButtonDisabled = intent.getBooleanExtra(EXTRA_IS_NEW_FOLDER_BUTTON_DISABLED, false);
        mIsSortButtonDisabled = intent.getBooleanExtra(EXTRA_IS_SORT_BUTTON_DISABLED, false);
        mIsQuitButtonEnabled = intent.getBooleanExtra(EXTRA_IS_QUIT_BUTTON_ENABLED, false);
        mChoiceType = (ExFilePicker.ChoiceType) intent.getSerializableExtra(EXTRA_CHOICE_TYPE);
        mSortingType = (ExFilePicker.SortingType) intent.getSerializableExtra(EXTRA_SORTING_TYPE);
        mCurrentDirectory = getStartDirectory(intent);
    }

    private int calculateGridColumnsCount() {
        return (int) (getResources().getDisplayMetrics().widthPixels / getResources().getDimension(R.dimen.files_grid_item_size));
    }

    private void showNewFolderDialog() {
        NewFolderDialog dialog = new NewFolderDialog(this);
        dialog.setOnNewFolderNameEnteredListener(this);
        dialog.show();
    }

    private void toggleViewMode() {
        MenuItem menuItem = mToolbar.getMenu().findItem(R.id.change_view);
        if (mAdapter.isGridModeEnabled()) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            menuItem.setIcon(Utils.attrToResId(this, R.attr.efp__ic_action_grid));
            menuItem.setTitle(R.string.efp__action_grid);
            mAdapter.setGridModeEnabled(false);
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, calculateGridColumnsCount()));
            menuItem.setIcon(Utils.attrToResId(this, R.attr.efp__ic_action_list));
            menuItem.setTitle(R.string.efp__action_list);
            mAdapter.setGridModeEnabled(true);
        }
        setChangeViewIcon(mToolbar.getMenu());
    }

    private void setMultiChoiceModeEnabled(boolean enabled) {
        mIsMultiChoiceModeEnabled = enabled;
        mToolbar.setMultiChoiceModeEnabled(enabled);
        mAdapter.setMultiChoiceModeEnabled(enabled);
        setChangeViewIcon(mToolbar.getMenu());
    }

    private void setChangeViewIcon(@NonNull Menu menu) {
        MenuItem item = menu.findItem(R.id.change_view);
        if (item != null) {
            item.setIcon(Utils.attrToResId(this, mAdapter.isGridModeEnabled() ? R.attr.efp__ic_action_list : R.attr.efp__ic_action_grid));
            item.setTitle(mAdapter.isGridModeEnabled() ? R.string.efp__action_list : R.string.efp__action_grid);
        }
    }

    private void finishWithResult(@NonNull File path, @NonNull String file) {
        finishWithResult(path, new ArrayList<>(Collections.singletonList(file)));
    }

    private void finishWithResult(@NonNull File path, @NonNull List<String> files) {
        String resultPath = path.getAbsolutePath();
        if (!resultPath.endsWith("/")) {
            resultPath += "/";
        }
        ExFilePickerResult result = new ExFilePickerResult(resultPath, files);
        Intent intent = new Intent();
        intent.putExtra(ExFilePickerResult.class.getCanonicalName(), result);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setupViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new FilesListAdapter();
        mAdapter.setOnListItemClickListener(this);
        mAdapter.setCanChooseOnlyFiles(mChoiceType == ExFilePicker.ChoiceType.FILES);
        mRecyclerView.setAdapter(mAdapter);
        mToolbar = (FilesListToolbar) findViewById(R.id.toolbar);
        mToolbar.setOnMenuItemClickListener(this);
        mToolbar.setNavigationOnClickListener(this);
        mToolbar.setQuitButtonEnabled(mIsQuitButtonEnabled);
        mToolbar.setMultiChoiceModeEnabled(false);
        Menu menu = mToolbar.getMenu();
        menu.findItem(R.id.ok).setVisible(mChoiceType == ExFilePicker.ChoiceType.DIRECTORIES);
        menu.findItem(R.id.new_folder).setVisible(!mIsNewFolderButtonDisabled);
        menu.findItem(R.id.sort).setVisible(!mIsSortButtonDisabled);
        mEmptyView = findViewById(R.id.empty_view);
    }

    @NonNull
    private File getStartDirectory(@NonNull Intent intent) {
        File path = null;
        String startPath = intent.getStringExtra(EXTRA_START_DIRECTORY);
        if (startPath != null && startPath.length() > 0) {
            File tmp = new File(startPath);
            if (tmp.exists() && tmp.isDirectory()) {
                path = tmp;
            }
        }
        if (path == null) {
            path = new File("/");
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                path = Environment.getExternalStorageDirectory();
            }
        }
        return path;
    }
}
