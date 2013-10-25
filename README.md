ExFilePicker
============

ExFilePicker is an open source android library that allows developers to easily implement choosing files and directories in application.

## Features

It can:
* Choose one file
* Choose one directory
* Choose few files and/or directories
* Filter by file extension

## Usage

__1.__ Add ActionBarSherlock as a dependency to ExFilePicker library

__2.__ Add ExFilePicker as a dependency to your project

__3.__ Add next code into your project where is needed:

```java
import ru.bartwell.exfilepicker.ExFilePicker;
import ru.bartwell.exfilepicker.ExFilePickerParcelObject;

// ...
	
	private static final int EX_FILE_PICKER_RESULT = 0;

	// ...
	
		Intent intent = new Intent(getApplicationContext(), ru.bartwell.exfilepicker.ExFilePickerActivity.class);
		startActivityForResult(intent, EX_FILE_PICKER_RESULT);

	// ...
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == EX_FILE_PICKER_RESULT) {
			if (data != null) {
				ExFilePickerParcelObject object = (ExFilePickerParcelObject) data.getParcelableExtra(ExFilePickerParcelObject.class.getCanonicalName());
				if (object.count > 0) {
					// Here is object contains selected files names and path
				}
			}
		}
	}
```
__4.__ Add activity in your Manufest:

```xml
<activity android:name="ru.bartwell.exfilepicker.ExFilePickerActivity" />
```

## Configuration

ExFilePicker can be configurated via intent extras.

* SET_ONLY_ONE_ITEM - boolean; if true, user can select only one item. False by default.

* SET_FILTER_BY_EXTENSION - String[]; files extensions to show.

* SET_CHOICE_TYPE - int; one of CHOICE_TYPE_ALL, CHOICE_TYPE_FILES or CHOICE_TYPE_DIRECTORIES. Set what user can select - only files, only directories or both. Both by default.

* SET_START_DIRECTORY - String. This path will be open when ExFilePicker activity will called.

Look example in ExFilePickerSample.

## Sample Application

[![Get it on Google Play](http://www.android.com/images/brand/get_it_on_play_logo_small.png)](http://play.google.com/store/apps/details?id=ru.bartwell.exfilepickersample)


## License

The MIT License (MIT)

Copyright (c) 2013 Artem Bazhanov

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
