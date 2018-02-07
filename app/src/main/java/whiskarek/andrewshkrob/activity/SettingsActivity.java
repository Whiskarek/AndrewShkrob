package whiskarek.andrewshkrob.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.main.MainActivity;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_settings, new SettingsFragment());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Resources.Theme getTheme() {
        final Resources.Theme theme = super.getTheme();
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        final boolean themeDark =
                sharedPreferences.getBoolean(getString(R.string.pref_key_theme_dark), false);
        if (themeDark == true) {
            theme.applyStyle(R.style.AppThemeDark, true);
        } else {
            theme.applyStyle(R.style.AppThemeLight, true);
        }

        return theme;
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
            addPreferencesFromResource(R.xml.preferences);
            final ListPreference listPreferenceSortType = (ListPreference)
                    findPreference(getString(R.string.pref_key_sort_type));
            final String sortArray[] = getResources().getStringArray(R.array.pref_sort_entries);
            SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
            final int sortTypePos = Integer.parseInt(
                    sharedPreferences.getString(getString(R.string.pref_key_sort_type), "0"));
            final String sortTypeName = sortArray[sortTypePos];
            listPreferenceSortType.setSummary(sortTypeName);
        }

        @Override
        public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String key) {
            if (key.equals(getString(R.string.pref_key_theme_dark))) {
                Log.i(getString(R.string.log_tag_preferences),
                        "Preference \"Theme\" was changed to "
                                + String.valueOf(sharedPreferences.getBoolean(key, false)));
                getActivity().recreate();
            } else if (key.equals(getString(R.string.pref_key_model_solid))) {
                Log.i(getString(R.string.log_tag_preferences),
                        "Preference \"Model\" was changed to "
                                + String.valueOf(sharedPreferences.getBoolean(key, false)));
            } else if (key.equals(getString(R.string.pref_key_sort_type))) {
                Log.i(getString(R.string.log_tag_preferences),
                        "Preference \"Sort\" was changed to "
                                + String.valueOf(sharedPreferences.getString(key, "0")));
                final ListPreference listPreferenceSortType = (ListPreference)
                        findPreference(getString(R.string.pref_key_sort_type));
                final String sortArray[] = getResources().getStringArray(R.array.pref_sort_entries);
                final int sortTypePos = Integer.parseInt(sharedPreferences.getString(key, "0"));
                final String sortTypeName = sortArray[sortTypePos];
                listPreferenceSortType.setSummary(sortTypeName);
            } else if (key.equals(getString(R.string.pref_key_show_welcome_page_on_next_load))) {
                Log.i(getString(R.string.log_tag_preferences),
                        "Preference \"Show Welcome Page\" was changed to "
                                + String.valueOf(sharedPreferences.getBoolean(key, false)));
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
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }
    }
}