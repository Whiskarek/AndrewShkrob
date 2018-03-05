package whiskarek.andrewshkrob.activity.launcher.fragment.desktop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.view.adapter.MenuViewPagerAdapter;

public class DesktopFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_desktop, container, false);

        final ViewPager viewPager = view.findViewById(R.id.desktop_screen_holder);
        viewPager.setAdapter(new MenuViewPagerAdapter(getFragmentManager(), Arrays.asList((Fragment) Screen.getScreen(1), Screen.getScreen(2))));

        return view;
    }

}
