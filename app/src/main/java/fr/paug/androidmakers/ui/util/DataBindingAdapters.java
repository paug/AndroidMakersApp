package fr.paug.androidmakers.ui.util;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import fr.paug.androidmakers.R;

public final class DataBindingAdapters {

    @BindingAdapter("img_src")
    public static void setDataIcon(ImageView imageView, int iconID) {
        imageView.setImageResource(iconID);
    }

    @BindingAdapter("imageUrl")
    public static void setSpeakerImageUrl(ImageView imageView, String url) {
        final Context context = imageView.getContext();

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .bitmapTransform(new CircleCrop())
                .placeholder(R.drawable.ic_person_black_24dp);

        Glide.with(context)
                .load(String.format("http://androidmakers.fr/img/people/%s", url))
                .apply(options)
                .into(imageView);
    }

    @BindingAdapter(value = "venueCoverUrl")
    public static void setVenueCoverImage(ImageView imageView, String venueImageURL) {
        final Context context = imageView.getContext();
        if (!TextUtils.isEmpty(venueImageURL)) {
            RequestOptions options = new RequestOptions()
                    .fitCenter()
                    .placeholder(R.color.light_grey);

            Glide.with(context)
                    .load(String.format("http://androidmakers.fr/img/venue/%s", venueImageURL))
                    .apply(options)
                    .into(imageView);
        } else {
            imageView.setImageDrawable(null);
        }
    }

}