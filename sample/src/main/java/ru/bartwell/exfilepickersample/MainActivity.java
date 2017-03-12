package ru.bartwell.exfilepickersample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import ru.bartwell.exfilepicker.ExFilePicker;
import ru.bartwell.exfilepicker.data.ExFilePickerResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int EX_FILE_PICKER_RESULT = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.choose_button).setOnClickListener(this);
    }

    @Override
    public void onClick(@NonNull View v) {
        ExFilePicker exFilePicker = new ExFilePicker();
        if (((AppCompatCheckBox) findViewById(R.id.only_one_item)).isChecked()) {
            exFilePicker.setCanChooseOnlyOneItem(true);
        }
        if (((AppCompatCheckBox) findViewById(R.id.filter_listed)).isChecked()) {
            exFilePicker.setShowOnlyExtensions("jpg", "jpeg");
        }
        if (((AppCompatCheckBox) findViewById(R.id.filter_exclude)).isChecked()) {
            exFilePicker.setExceptExtensions("jpg");
        }
        if (((AppCompatCheckBox) findViewById(R.id.disable_new_folder_button)).isChecked()) {
            exFilePicker.setNewFolderButtonDisabled(true);
        }
        if (((AppCompatCheckBox) findViewById(R.id.disable_sort_button)).isChecked()) {
            exFilePicker.setSortButtonDisabled(true);
        }
        if (((AppCompatCheckBox) findViewById(R.id.enable_quit_button)).isChecked()) {
            exFilePicker.setQuitButtonEnabled(true);
        }
        if (((AppCompatCheckBox) findViewById(R.id.reverse_sorting)).isChecked()) {
            exFilePicker.setSortingType(ExFilePicker.SortingType.NAME_DESC);
        }
        if (((AppCompatCheckBox) findViewById(R.id.start_from_root)).isChecked()) {
            exFilePicker.setStartDirectory("/");
        }
        if (((AppCompatCheckBox) findViewById(R.id.first_item_as_up)).isChecked()) {
            exFilePicker.setUseFirstItemAsUpEnabled(true);
        }
        if (((AppCompatCheckBox) findViewById(R.id.hide_hidden_files)).isChecked()) {
            exFilePicker.setHideHiddenFilesEnabled(true);
        }
        int checkedChoiceRadio = ((RadioGroup) findViewById(R.id.choice_type)).getCheckedRadioButtonId();
        if (checkedChoiceRadio == R.id.choice_type_files) {
            exFilePicker.setChoiceType(ExFilePicker.ChoiceType.FILES);
        } else if (checkedChoiceRadio == R.id.choice_type_directories) {
            exFilePicker.setChoiceType(ExFilePicker.ChoiceType.DIRECTORIES);
        }
        exFilePicker.start(this, EX_FILE_PICKER_RESULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == EX_FILE_PICKER_RESULT) {
            ExFilePickerResult result = ExFilePickerResult.getFromIntent(data);
            if (result != null && result.getCount() > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < result.getCount(); i++) {
                    stringBuilder.append(result.getNames().get(i));
                    if (i < result.getCount() - 1) stringBuilder.append(", ");
                }
                ((TextView) findViewById(R.id.result)).setText("Count: " + result.getCount() + "\n" + "Path: " + result.getPath() + "\n" + "Selected: " + stringBuilder.toString());
            }
        }
    }
}
