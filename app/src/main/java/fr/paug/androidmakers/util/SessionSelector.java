package fr.paug.androidmakers.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by stan on 26/03/2017.
 */

public class SessionSelector {

    private static final String PREF_SELECTED_SESSIONS = "selected_sessions";
    private SharedPreferences mSharedPreferences;

    private Set<String> mSessionsSelected;

    private SessionSelector() {
    }

    public static SessionSelector getInstance() {
        return SingletonHolder.instance;
    }

    public void init(Context context) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mSessionsSelected = mSharedPreferences.getStringSet(PREF_SELECTED_SESSIONS,
                new HashSet<String>());
    }

    public void setSessionSelected(int id, boolean selected) {
        String idString = Integer.toString(id);
        if (selected) {
            mSessionsSelected.add(idString);
        } else {
            mSessionsSelected.remove(idString);
        }
        save();
    }

    public boolean hasSelected() {
        return !mSessionsSelected.isEmpty();
    }

    public boolean isSelected(int id) {
        return mSessionsSelected.contains(Integer.toString(id));
    }

    public Set<String> getSessionsSelected() {
        return mSessionsSelected;
    }

    public void setSessionsSelected(Set<String> sessionsSelected) {
        mSessionsSelected = sessionsSelected;
    }

    private void save() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putStringSet(PREF_SELECTED_SESSIONS, mSessionsSelected);
        editor.apply();
    }

    private static class SingletonHolder {
        private final static SessionSelector instance = new SessionSelector();
    }
}
