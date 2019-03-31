package fr.paug.androidmakers.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

import fr.paug.androidmakers.manager.AgendaRepository;

public final class SessionSelector {

    private static final String PREF_SELECTED_SESSIONS = "selected_sessions";
    private SharedPreferences mSharedPreferences;

    private Set<String> mSessionsSelected;

    private SessionSelector() {
        // no instance
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
        String idString = AgendaRepository.Companion.getCURRENT_YEAR_NODE() + "_" + Integer.toString(id);
        if (selected) {
            mSessionsSelected.add(idString);
        } else {
            mSessionsSelected.remove(idString);
        }
        save();
    }

    public boolean isSelected(int id) {
        return mSessionsSelected.contains(AgendaRepository.Companion.getCURRENT_YEAR_NODE() + "_" + Integer.toString(id));
    }

    public Set<String> getSessionsSelected() {
        return mSessionsSelected;
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