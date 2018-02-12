package whiskarek.andrewshkrob.activity.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.main.MainActivity;
import whiskarek.andrewshkrob.activity.settings.fragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity implements PreferenceFragmentCompat.OnPreferenceStartScreenCallback {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        final Toolbar toolbar = findViewById(R.id.toolbar);
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
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public Resources.Theme getTheme() {
        final Resources.Theme theme = super.getTheme();
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        final boolean themeDark =
                sharedPreferences.getBoolean(getString(R.string.pref_key_theme_dark), false);
        if (themeDark) {
            theme.applyStyle(R.style.AppThemeDark, true);
        } else {
            theme.applyStyle(R.style.AppThemeLight, true);
        }

        return theme;
    }

    @Override
    public boolean onPreferenceStartScreen(final PreferenceFragmentCompat caller, final PreferenceScreen pref) {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        final SettingsFragment fragment = new SettingsFragment();
        final Bundle args = new Bundle();
        args.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, pref.getKey());
        fragment.setArguments(args);
        ft.replace(R.id.fragment_settings, fragment, pref.getKey());
        ft.addToBackStack(pref.getKey());
        ft.commit();
        return true;
    }


}