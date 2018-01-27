package fr.paug.androidmakers.model;

import android.support.annotation.DrawableRes;
import android.text.TextUtils;

import fr.paug.androidmakers.R;

/**
 * The class description here.
 *
 * @author Adrien Vitti
 * @since 2018.01.21
 */

public class Ribbon {

    public enum RibbonType {
        NONE, GDE, GDG;

        @DrawableRes
        public int getRibbonIcon() {
            switch (this) {
                case GDE:
                    return R.drawable.gde_logo;
                case GDG:
                    return R.drawable.gdg_logo;
                default:
                    return -1;
            }
        }

        @DrawableRes
        public int getBadgeIcon() {
            switch (this) {
                case GDE:
                    return R.drawable.gde_badge;
                case GDG:
                    return R.drawable.gdg_badge;
                default:
                    return -1;
            }
        }
    }

    public final RibbonType ribbonType;
    public final String title;
    public final String link;

    public Ribbon(String ribbonName, String title, String link) {
        this.ribbonType = getRibbonType(ribbonName);
        this.title = title;
        this.link = link;
    }

    private static RibbonType getRibbonType(String ribbonName) {
        if (!TextUtils.isEmpty(ribbonName)) {
            if (ribbonName.equalsIgnoreCase("GDE")) {
                return RibbonType.GDE;
            } else if (ribbonName.equalsIgnoreCase("GDG")) {
                return RibbonType.GDG;
            }
        }
        return RibbonType.NONE;
    }

}
