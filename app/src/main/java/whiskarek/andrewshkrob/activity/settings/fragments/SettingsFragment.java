package whiskarek.andrewshkrob.activity.settings.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.yandex.metrica.YandexMetrica;

import whiskarek.andrewshkrob.LauncherApplication;
import whiskarek.andrewshkrob.R;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        final ListPreference listPreferenceSortType = (ListPreference)
                findPreference(getString(R.string.pref_key_sort_type));

        if (listPreferenceSortType != null) {
            final String sortArray[] = getResources().getStringArray(R.array.pref_sort_entries);
            final SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
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
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String key) {
        if (key.equals(getString(R.string.pref_key_theme_dark))) {
            YandexMetrica.reportEvent(getString(R.string.log_tag_preferences),
                    "Preference \"Theme\" was changed to "
                            + sharedPreferences.getBoolean(key, false));
            getActivity().recreate();
        } else if (key.equals(getString(R.string.pref_key_model_solid))) {
            YandexMetrica.reportEvent(getString(R.string.log_tag_preferences),
                    "Preference \"Model\" was changed to "
                            + sharedPreferences.getBoolean(key, false));
        } else if (key.equals(getString(R.string.pref_key_sort_type))) {
            YandexMetrica.reportEvent(getString(R.string.log_tag_preferences),
                    "Preference \"Sort\" was changed to "
                            + sharedPreferences.getString(key, "0"));
            final ListPreference listPreferenceSortType = (ListPreference)
                    findPreference(key);
            if (listPreferenceSortType != null) {
                final String sortArray[] = getResources().getStringArray(R.array.pref_sort_entries);
                final int sortTypePos = Integer.parseInt(sharedPreferences.getString(key, "0"));
                final String sortTypeName = sortArray[sortTypePos];
                listPreferenceSortType.setSummary(sortTypeName);
            }
        } else if (key.equals(getString(R.string.pref_key_show_welcome_page_on_next_load))) {
            YandexMetrica.reportEvent(getString(R.string.log_tag_preferences),
                    "Preference \"Show Welcome Page\" was changed to "
                            + sharedPreferences.getBoolean(key, false));
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

            YandexMetrica.reportEvent(getString(R.string.log_tag_preferences),
                    "Preference \"Change background frequency\" was changed to "
                            + sharedPreferences.getString(key, "0"));
            ((LauncherApplication) getActivity().getApplication()).updateTimer();
        } else if (key.equals(getString(R.string.pref_key_unique_background_photos))) {
            ((LauncherApplication) getActivity().getApplication()).updateTimer();
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