package fr.paug.androidmakers.util;

import android.content.res.ColorStateList;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Property;
import android.view.View;

public class UIUtils {

    public static final Property<View, Integer> BACKGROUND_TINT
            = new Property<View, Integer>(Integer.class, "backgroundTint") {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void set(View view, Integer color) {
            view.setBackgroundTintList(ColorStateList.valueOf(color));
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public Integer get(View view) {
            return view.getBackgroundTintList().getDefaultColor();
        }
    };

}
