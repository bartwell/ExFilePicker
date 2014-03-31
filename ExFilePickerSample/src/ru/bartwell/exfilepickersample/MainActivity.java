package ru.bartwell.exfilepickersample;

import ru.bartwell.exfilepicker.ExFilePicker;
import ru.bartwell.exfilepicker.ExFilePickerParcelObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static final int EX_FILE_PICKER_RESULT = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);

		findViewById(R.id.choose_button).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(), ru.bartwell.exfilepicker.ExFilePickerActivity.class);
				if (((CheckBox) findViewById(R.id.only_one_item)).isChecked()) intent.putExtra(ExFilePicker.SET_ONLY_ONE_ITEM, true);
				if (((CheckBox) findViewById(R.id.filter_listed)).isChecked()) intent.putExtra(ExFilePicker.SET_FILTER_LISTED, new String[] { "jpg", "jpeg" });
				if (((CheckBox) findViewById(R.id.filter_exclude)).isChecked()) intent.putExtra(ExFilePicker.SET_FILTER_EXCLUDE, new String[] { "jpg" });
				if (((CheckBox) findViewById(R.id.disable_new_folder_button)).isChecked()) intent.putExtra(ExFilePicker.DISABLE_NEW_FOLDER_BUTTON, true);

				int checkedChoiceRadio = ((RadioGroup) findViewById(R.id.choice_type)).getCheckedRadioButtonId();
				if (checkedChoiceRadio == R.id.choice_type_files) intent.putExtra(ExFilePicker.SET_CHOICE_TYPE, ExFilePicker.CHOICE_TYPE_FILES);
				if (checkedChoiceRadio == R.id.choice_type_directories) intent.putExtra(ExFilePicker.SET_CHOICE_TYPE, ExFilePicker.CHOICE_TYPE_DIRECTORIES);

				startActivityForResult(intent, EX_FILE_PICKER_RESULT);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == EX_FILE_PICKER_RESULT) {
			if (data != null) {
				ExFilePickerParcelObject object = (ExFilePickerParcelObject) data.getParcelableExtra(ExFilePickerParcelObject.class.getCanonicalName());
				if (object.count > 0) {
					StringBuffer buffer = new StringBuffer();
					for (int i = 0; i < object.count; i++) {
						buffer.append(object.names.get(i));
						if (i < object.count - 1) buffer.append(", ");
					}
					((TextView) findViewById(R.id.result)).setText("Count: " + object.count + "\n" + "Path: " + object.path + "\n" + "Selected: " + buffer.toString());
				}
			}
		}
	}

}
