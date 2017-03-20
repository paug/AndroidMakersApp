package fr.paug.androidmakers.model;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by stan on 18/03/2017.
 */
public class Speaker {

    public final String name;
    public final String bio;
    public final String company;
    public final String surname;
    public final String thumbnailUrl;

    public Speaker(String name, String bio, String company, String surname, String thumbnailUrl) {
        this.name = name;
        this.bio = bio;
        this.company = company;
        this.surname = surname;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getFullNameAndCompany() {
        return this.name + " " + this.surname + (TextUtils.isEmpty(company) ? "" : ", " + this.company);
    }

    @BindingAdapter("imageUrl")
    public static void setSpeakerImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load("http://androidmakers.fr/img/people/" + url).into(imageView);
    }
}
