package whiskarek.andrewshkrob.activity.welcomepage.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yandex.metrica.YandexMetrica;

import whiskarek.andrewshkrob.R;

public class ThemeFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_theme, container, false);

        final RadioButton rbLight = view.findViewById(R.id.radio_button_theme_light);
        final RadioButton rbDark = view.findViewById(R.id.radio_button_theme_dark);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        final boolean themeDark = sharedPreferences
                .getBoolean(getString(R.string.pref_key_theme_dark), false);

        if (themeDark) {
            rbDark.setChecked(true);
            rbLight.setChecked(false);
        } else {
            rbDark.setChecked(false);
            rbLight.setChecked(true);
        }

        final RadioGroup rgTheme = view.findViewById(R.id.radio_group_theme);
        rgTheme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button_theme_light: {
                        if (themeDark) {
                            sharedPreferences.edit()
                                    .putBoolean(getString(R.string.pref_key_theme_dark), false).apply();
                        }
                        break;
                    }
                    case R.id.radio_button_theme_dark: {
                        if (!themeDark) {
                            sharedPreferences.edit()
                                    .putBoolean(getString(R.string.pref_key_theme_dark), true).apply();
                        }

                        break;
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String key) {
        if (key.equals(getString(R.string.pref_key_theme_dark))) {
            YandexMetrica.reportEvent(getString(R.string.log_tag_preferences),
                    "Preference \"Theme\" was changed to "
                            + sharedPreferences.getBoolean(key, false));
            getActivity().recreate();
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
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

}
