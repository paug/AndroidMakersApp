package fr.paug.androidmakers.model;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import fr.paug.androidmakers.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * @author adrien
 * @since 2017.03.22
 */
public class SocialNetworkHandle {

    public final String name;
    public final String link;

    public SocialNetworkHandle(String name, String link) {
        this.name = name;
        this.link = link;
    }
}
