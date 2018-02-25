package whiskarek.andrewshkrob.activities.welcomepage.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import whiskarek.andrewshkrob.R;

public class ModelTypeFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(final Bundle savedInstanceState, final String rootKey) {
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_model_type, container, false);

        final RadioButton modelTypeDefault = view.findViewById(R.id.radio_button_model_default);
        final RadioButton modelTypeSolid = view.findViewById(R.id.radio_button_model_solid);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        final boolean modelSolid = sharedPreferences
                .getBoolean(getString(R.string.pref_key_model_solid), false);

        if (modelSolid) {
            modelTypeSolid.setChecked(true);
            modelTypeDefault.setChecked(false);
        } else {
            modelTypeSolid.setChecked(false);
            modelTypeDefault.setChecked(true);
        }

        final RadioGroup rgModel = view.findViewById(R.id.radio_group_model_type);
        rgModel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_button_model_default: {
                        if (modelSolid) {
                            sharedPreferences.edit()
                                    .putBoolean(getString(R.string.pref_key_model_solid), false).apply();
                        }
                        break;
                    }
                    case R.id.radio_button_model_solid: {
                        if (!modelSolid) {
                            sharedPreferences.edit()
                                    .putBoolean(getString(R.string.pref_key_model_solid), true).apply();
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
