package fr.paug.androidmakers.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

public final class SessionSelector {

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
        mSessionsSelected = new HashSet<>();
        Set<String> prefSet = mSharedPreferences.getStringSet(PREF_SELECTED_SESSIONS, null);
        if (prefSet != null) {
            mSessionsSelected.addAll(prefSet);
        }
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
