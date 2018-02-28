package whiskarek.andrewshkrob.activity.launcher.fragment.menu.settings;

import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.util.Log;
import android.view.View;

import java.util.List;

import whiskarek.andrewshkrob.AppInfo;
import whiskarek.andrewshkrob.LauncherExecutors;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.Sort;
import whiskarek.andrewshkrob.viewmodel.AppInfoViewModel;

public class SettingsFragment extends PreferenceFragmentCompat implements
        SharedPreferences.OnSharedPreferenceChangeListener{

    private AppInfoViewModel mSortType;

    @Override
    public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        mSortType = ViewModelProviders.of(getActivity()).get(AppInfoViewModel.class);

        final ListPreference listPreferenceSortType = (ListPreference)
                findPreference(getString(R.string.pref_key_sort_type));

        if (listPreferenceSortType != null) {
            final String sortArray[] = getResources().getStringArray(R.array.pref_sort_entries);
            final SharedPreferences sharedPreferences =
                    getPreferenceScreen().getSharedPreferences();
            final int sortTypePos = Integer.parseInt(
                    sharedPreferences.getString(getString(R.string.pref_key_sort_type), "0"));
            final String sortTypeName = sortArray[sortTypePos];
            listPreferenceSortType.setSummary(sortTypeName);
        }

        final ListPreference listPreferenceBackgroundChangeFrequency = (ListPreference)
                findPreference(getString(R.string.pref_key_background_change_frequency));
        if (listPreferenceBackgroundChangeFrequency != null) {
            final String updateFrequency[] = getResources()
                    .getStringArray(R.array.pref_background_change_frequency_entries);
            final int updateFrequencyPos = getUpdateFrequencyPos();

            listPreferenceBackgroundChangeFrequency.setSummary(
                    updateFrequencyPos == -1 ? "" : updateFrequency[updateFrequencyPos]
            );
        }
    }

    private int getUpdateFrequencyPos() {
        final SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        final String updateFrequencyValues[] = getResources()
                .getStringArray(R.array.pref_background_change_frequency_entry_values);

        final String value = sharedPreferences.getString(
                getString(R.string.pref_key_background_change_frequency),
                updateFrequencyValues[0]
        );

        for (int i = 0; i < updateFrequencyValues.length; i++) {
            if (value.equals(updateFrequencyValues[i])) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences,
                                          final String key) {
        if (key.equals(getString(R.string.pref_key_theme_dark))) {
            getActivity().recreate();
        } else if (key.equals(getString(R.string.pref_key_model_solid))) {

        } else if (key.equals(getString(R.string.pref_key_sort_type))) {
            final ListPreference listPreferenceSortType = (ListPreference)
                    findPreference(key);
            if (listPreferenceSortType != null) {
                final String sortArray[] = getResources().getStringArray(R.array.pref_sort_entries);
                final int sortTypePos =
                        Integer.parseInt(sharedPreferences.getString(key, "0"));
                final String sortTypeName = sortArray[sortTypePos];
                listPreferenceSortType.setSummary(sortTypeName);
                mSortType.setSortType(sortTypePos);

                Log.d("Launcher", "Post new sort type: " + sortTypePos);
            }
        } else if (key.equals(getString(R.string.pref_key_show_welcome_page_on_next_load))) {

        } else if (key.equals(getString(R.string.pref_key_background_change_frequency))) {
            final ListPreference listPreferenceBackgroundChangeFrequency = (ListPreference)
                    findPreference(key);
            if (listPreferenceBackgroundChangeFrequency != null) {
                final String updateFrequency[] = getResources()
                        .getStringArray(R.array.pref_background_change_frequency_entries);
                final int updateFrequencyPos = getUpdateFrequencyPos();
                listPreferenceBackgroundChangeFrequency.setSummary(
                        updateFrequencyPos == -1 ? "" : updateFrequency[updateFrequencyPos]
                );
            }
        } else if (key.equals(getString(R.string.pref_key_unique_background_photos))) {
        }
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}