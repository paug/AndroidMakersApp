package fr.paug.androidmakers.model;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by stan on 18/03/2017.
 */
public class Speaker {

    public final String name;
    public final String bio;
    public final String company;
    public final String surname;
    public final String thumbnailUrl;
    public final List<Ribbon> ribbonList;
    public final boolean rockstar;
    public final List<SocialNetworkHandle> socialNetworkHandles;

    public Speaker(String name, String bio, String company, String surname, String thumbnailUrl, String rockstar, List<SocialNetworkHandle> socialNetworkHandleList, List<Ribbon> ribbonList) {
        this.name = name;
        this.bio = bio;
        this.company = company;
        this.surname = surname;
        this.thumbnailUrl = thumbnailUrl;
        this.rockstar = Boolean.parseBoolean(rockstar);
        this.socialNetworkHandles = socialNetworkHandleList;
        this.ribbonList = ribbonList;
    }

    public String getFullNameAndCompany() {
        return this.name + " " + this.surname + (TextUtils.isEmpty(company) ? "" : ", " + this.company);
    }

    /**
     * Gets the main ribbon.
     * The main ribbon is the first ribbon of the list.
     *
     * @return the main ribbon, or null if the ribbon list is empty or null.
     */
    @Nullable
    public Ribbon getMainRibbon() {
        if (ribbonList != null && !ribbonList.isEmpty()) {
            return ribbonList.get(0);
        }
        return null;
    }

}