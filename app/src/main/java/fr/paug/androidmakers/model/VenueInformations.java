package fr.paug.androidmakers.model;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @author Adrien Vitti
 * @since 2018.01.14
 */

public class VenueInformations {

    public final String venueName;
    public final String venueAddress;
    public final String venueDirections;
    public final String venueImageURL;

    public VenueInformations(String venueName, String venueAddress, String venueDirections, String venueImageURL) {
        this.venueName = venueName;
        this.venueAddress = venueAddress;
        this.venueDirections = venueDirections;
        this.venueImageURL = venueImageURL;
    }

    @BindingAdapter(value = "venueCoverUrl")
    public static void setVenueCoverImage(ImageView imageView, String venueImageURL) {
        final Context context = imageView.getContext();
        if (TextUtils.isEmpty(venueImageURL) == false) {
            Glide.with(context)
                    .load("http://androidmakers.fr/img/people/" + venueImageURL)
                    .into(imageView);
        } else
            imageView.setImageDrawable(null);
    }
}
