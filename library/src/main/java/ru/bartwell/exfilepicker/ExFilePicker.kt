package ru.bartwell.exfilepicker

import android.content.Context
import android.content.Intent
import ru.bartwell.exfilepicker.data.config.Config
import ru.bartwell.exfilepicker.ui.ExFilePickerActivity

class ExFilePicker {

    fun createIntent(context: Context, config: Config): Intent {
        configHolder = config
        return Intent(context, ExFilePickerActivity::class.java)
    }

    fun getResult(intent: Intent?): Array<String>? {
        if (intent?.hasExtra(EXTRA_RESULT) == true) {
            return intent.getStringArrayExtra(EXTRA_RESULT)
        }
        return null
    }

    companion object {
        internal const val EXTRA_RESULT = "result"
        internal var configHolder = Config()
    }
}
