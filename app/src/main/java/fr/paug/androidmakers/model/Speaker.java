package fr.paug.androidmakers.model;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import fr.paug.androidmakers.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by stan on 18/03/2017.
 */
public class Speaker {

    public final String name;
    public final String bio;
    public final String company;
    public final String surname;
    public final String thumbnailUrl;
    public final boolean rockstar;
    public final List<SocialNetworkHandle> socialNetworkHandles;

    public Speaker(String name, String bio, String company, String surname, String thumbnailUrl, String rockstar, List<SocialNetworkHandle> socialNetworkHandleList) {
        this.name = name;
        this.bio = bio;
        this.company = company;
        this.surname = surname;
        this.thumbnailUrl = thumbnailUrl;
        this.rockstar = Boolean.parseBoolean(rockstar);
        this.socialNetworkHandles = socialNetworkHandleList;
    }

    public String getFullNameAndCompany() {
        return this.name + " " + this.surname + (TextUtils.isEmpty(company) ? "" : ", " + this.company);
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

}