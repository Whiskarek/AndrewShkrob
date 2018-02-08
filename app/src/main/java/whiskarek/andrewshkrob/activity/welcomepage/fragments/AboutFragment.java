package whiskarek.andrewshkrob.activity.welcomepage.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import whiskarek.andrewshkrob.R;

public class AboutFragment extends Fragment {

        @Nullable
        @Override
        public View onCreateView(final LayoutInflater inflater,
                                 @Nullable final ViewGroup container,
                                 @Nullable final Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.fragment_about, container, false);

            return view;

        }

}
