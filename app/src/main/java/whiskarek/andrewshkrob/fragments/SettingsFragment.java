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

public class SettingsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings, null);

        final RadioButton rbDefaultModel = (RadioButton) view.findViewById(R.id.rbDefaultModel);
        final RadioButton rbSolidModel = (RadioButton) view.findViewById(R.id.rbSolidModel);

        final int modelType = Settings.getModelType();

        if (modelType == Settings.Model.DEFAULT) {
            rbDefaultModel.setChecked(true);
        } else {
            rbSolidModel.setChecked(true);
        }

        final RadioGroup rgModule = (RadioGroup) view.findViewById(R.id.rgModule);
        rgModule.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                final int model = Settings.getModelType();
                switch (checkedId) {
                    case R.id.rbDefaultModel: {
                        Settings.setModelType(Settings.Model.DEFAULT);

                        if (model != Settings.Model.DEFAULT) {

                        }

                        break;
                    }
                    case R.id.rbSolidModel: {
                        Settings.setModelType(Settings.Model.SOLID);

                        if (model != Settings.Model.SOLID) {

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
