package fr.paug.androidmakers.ui.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;

import fr.paug.androidmakers.R;

// TODO remove this ?
public class AgendaFilterMenu implements MenuItem.OnMenuItemClickListener {
    private static final String PREF_FILTER_LANGUAGE_SELECTED = "filter_language_selected";
    private final SharedPreferences mSharedPreferences;
    private MenuFilterListener mMenuFilterListener;
    private String mTitleAll;
    private String mSelectedOption;
    private SubMenu mFilter;
    private String[] mLanguages;

    public AgendaFilterMenu(Context context, Menu menu, MenuInflater inflater) {
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        inflater.inflate(R.menu.filter, menu);
        MenuItem item = menu.findItem(R.id.filter);
        mFilter = item.getSubMenu();
        mFilter.setHeaderTitle(R.string.filter_language_header);
        mTitleAll = context.getString(R.string.filter_language_all);

        mSelectedOption = mSharedPreferences.getString(PREF_FILTER_LANGUAGE_SELECTED, null);

        refreshFilters();
    }

    public void setMenuFilterListener(MenuFilterListener menuFilterListener) {
        mMenuFilterListener = menuFilterListener;
    }

    public void setLanguages(String... languages) {
        mLanguages = languages;
        refreshFilters();
    }

    public String getLanguageFilter() {
        return mSelectedOption;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        String newOption = item.getTitle().toString();
        if (newOption.equals(mSelectedOption)) {
            return true;
        }
        mSelectedOption = newOption;
        if (mSelectedOption.equals(mTitleAll)) {
            mSelectedOption = null;
        }
        saveInPreference();
        refreshFilters();
        if (mMenuFilterListener != null) {
            mMenuFilterListener.onFilterChanged();
        }
        return true;
    }

    private void saveInPreference() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (mSelectedOption == null) {
            editor.remove(PREF_FILTER_LANGUAGE_SELECTED);
        } else {
            editor.putString(PREF_FILTER_LANGUAGE_SELECTED, mSelectedOption);
        }
        editor.apply();
    }

    private void refreshFilters() {
        mFilter.clear();
        addFilterItem(mTitleAll, mSelectedOption == null);
        if (mLanguages != null) {
            for (String language : mLanguages) {
                addFilterItem(language, language.equals(mSelectedOption));
            }
        }
    }

    private void addFilterItem(String label, boolean checked) {
        MenuItem menuItem = mFilter.add(label);
        menuItem.setCheckable(true);
        menuItem.setChecked(checked);
        menuItem.setOnMenuItemClickListener(this);
    }

    public interface MenuFilterListener {
        void onFilterChanged();
    }
}
