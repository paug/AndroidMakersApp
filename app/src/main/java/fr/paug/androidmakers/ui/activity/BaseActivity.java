package fr.paug.androidmakers.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import fr.paug.androidmakers.util.ThemeUtils;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeUtils.ensureRuntimeTheme(this);
        super.onCreate(savedInstanceState);
    }

}