ExFilePicker
============

ExFilePicker is an open source android library that allows developers to easily implement choosing files and directories in application.

## Screenshotes:

[![Screenshot](https://raw.github.com/bartwell/ExFilePicker/master/stuff/preview-screenshot1.png)](https://raw.github.com/bartwell/ExFilePicker/master/stuff/screenshot1.png) [![Screenshot](https://raw.github.com/bartwell/ExFilePicker/master/stuff/preview-screenshot2.png)](https://raw.github.com/bartwell/ExFilePicker/master/stuff/screenshot2.png) [![Screenshot](https://raw.github.com/bartwell/ExFilePicker/master/stuff/preview-screenshot3.png)](https://raw.github.com/bartwell/ExFilePicker/master/stuff/screenshot3.png)

## Features

It can:
* Choose one file
* Choose one directory
* Choose few files and/or directories
* Filter by file extension
* Sorting
* Creating directories

## Download

* Gradle:

```groovy
repositories {
    jcenter()
}

dependencies {
    compile 'ru.bartwell:exfilepicker:2.1'
}
```

* AAR:

[Download AAR](https://github.com/bartwell/ExFilePicker/tree/master/library/build/outputs/aar/library-release.aar)

## Usage

__1.__ Add ExFilePicker library as a dependency to your project

__2.__ Use methods from `ExFilePicker` class to launch picker activity and receive result in `onActivityResult()`:

```java
	private static final int EX_FILE_PICKER_RESULT = 0;

	// ...
	
		ExFilePicker exFilePicker = new ExFilePicker();
        exFilePicker.start(this, EX_FILE_PICKER_RESULT);

	// ...
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == EX_FILE_PICKER_RESULT) {
		    ExFilePickerResult result = ExFilePickerResult.getFromIntent(data);
			if (result != null && result.getCount() > 0) {
				// Here is object contains selected files names and path
			}
		}
	}
```

## Configuration

Class ExFilePicker have following methods for configuration:

* setCanChooseOnlyOneItem() - if true, user can select only one item. False by default.

* setShowOnlyExtensions() - only files with this extensions will showed.

* setExceptExtensions() - files with this extensions will excluded.

* setChoiceType() - one of value from ChoiceType. Set what user can select - only files, only directories or both. Both by default.

* setStartDirectory() - This path will be open when ExFilePicker activity will called.

* setNewFolderButtonDisabled() - if true, button "New folder" will not showing. You can also remove WRITE_EXTERNAL_STORAGE permission in this case.

* setSortButtonDisabled() - if true, button "Sort" will not showing.

* setQuitButtonEnabled() - if true, quit button will showing.

* setSortingType() - one of value from SortingType. Set default sorting. NAME_ASC by default.

* setUseFirstItemAsUpEnabled() - enable link to the parent directory as first item in list. 

Feel free to look sample.

## Customization

ExFilePicker library provide two themes for ExFilePicker's activity: dark and light (ExFilePickerThemeDark and ExFilePickerThemeLight respectively). If you need to customize ExFilePicker's activity view, you can extend your own theme from any ExFilePicker's theme and override needed options. To set theme you need specify activity in Manifest:

```xml
<activity
    android:name="ru.bartwell.exfilepicker.ui.activity.ExFilePickerActivity"
    android:theme="@style/ExFilePickerThemeDark"
    tools:replace="android:theme"/>
```

## Sample Application

Sample application is no more available on Google Play: account 'bartwell' was banned and all appeals was declined or ignored. To see how it works you can download debug version [here](https://github.com/bartwell/ExFilePicker/tree/master/sample/build/outputs/apk/sample-debug.apk).

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
