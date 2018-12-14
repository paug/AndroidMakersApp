package fr.paug.androidmakers.util;

import android.content.Context;
import android.util.TypedValue;

import fr.paug.androidmakers.R;

public final class ThemeUtils {

    private static final ThreadLocal<TypedValue> TYPED_VALUE = new ThreadLocal<TypedValue>() {
        @Override
        protected TypedValue initialValue() {
            return new TypedValue();
        }
    };

    private ThemeUtils() {
        // No instances
    }

    public static void ensureRuntimeTheme(Context context) {
        final TypedValue tv = TYPED_VALUE.get();
        context.getTheme().resolveAttribute(R.attr.runtimeTheme, tv, true);
        if (tv.resourceId <= 0) {
            throw new IllegalArgumentException("runtimeTheme not defined in the preview theme");
        }
        context.setTheme(tv.resourceId);
    }

}