package fr.paug.androidmakers.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import fr.paug.androidmakers.util.ThemeUtils

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ThemeUtils.ensureRuntimeTheme(this)
        super.onCreate(savedInstanceState)
    }

}