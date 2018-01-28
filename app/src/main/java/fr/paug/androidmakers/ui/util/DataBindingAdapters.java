package fr.paug.androidmakers.ui.util;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import fr.paug.androidmakers.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * @author Adrien Vitti
 * @since 2018.01.21
 */
public final class DataBindingAdapters {

    @BindingAdapter("img_src")
    public static void setDataIcon(ImageView imageView, int iconID) {
        imageView.setImageResource(iconID);
    }

    @BindingAdapter("imageUrl")
    public static void setSpeakerImageUrl(ImageView imageView, String url) {
        final Context context = imageView.getContext();
        Glide.with(context)
                .load("http://androidmakers.fr/img/people/" + url)
                .centerCrop()
                .bitmapTransform(new CropCircleTransformation(context))
                .placeholder(R.drawable.ic_person_black_24dp)
                .into(imageView);
    }

    @BindingAdapter(value = "venueCoverUrl")
    public static void setVenueCoverImage(ImageView imageView, String venueImageURL) {
        final Context context = imageView.getContext();
        if (TextUtils.isEmpty(venueImageURL) == false) {
            Glide.with(context)
                    .load("http://androidmakers.fr/img/venue/" + venueImageURL)
                    .into(imageView);
        } else {
            imageView.setImageDrawable(null);
        }
    }

}