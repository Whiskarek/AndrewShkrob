package whiskarek.andrewshkrob.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.Settings;

public class ThemeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_theme, null);
        ;

        final RadioButton rbLight = (RadioButton) view.findViewById(R.id.rbLight);
        final RadioButton rbDark = (RadioButton) view.findViewById(R.id.rbDark);

        final int theme = Settings.getTheme();

        if (theme == 1) {
            rbLight.setChecked(true);
        } else {
            rbDark.setChecked(true);
        }

        final RadioGroup rgTheme = (RadioGroup) view.findViewById(R.id.rgTheme);
        rgTheme.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final int theme = Settings.getTheme();
                switch (checkedId) {
                    case R.id.rbLight: {
                        Settings.setTheme(1);

                        if (theme != 1) {

                        }

                        break;
                    }
                    case R.id.rbDark: {
                        Settings.setTheme(2);

                        if (theme != 2) {

                        }

                        break;
                    }
                }
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
    }
}
