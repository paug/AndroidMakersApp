package fr.paug.androidmakers.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import fr.paug.androidmakers.model.RoomKt
import fr.paug.androidmakers.model.SessionKt
import fr.paug.androidmakers.util.ScheduleSessionHelper

import fr.paug.androidmakers.util.ThemeUtils
import io.openfeedback.android.model.Project

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeUtils.ensureRuntimeTheme(this)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        ScheduleSessionHelper.cancelToast()
    }
}