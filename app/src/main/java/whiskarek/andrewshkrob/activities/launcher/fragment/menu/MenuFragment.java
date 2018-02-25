package whiskarek.andrewshkrob.activities.launcher.fragment.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.view.adapter.MenuFragmentAdapter;

public class MenuFragment extends Fragment {

    private List<MenuScreenFragment> mMenuScreenFragments = new ArrayList<>();

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menu, container, false);

        final ViewPager mViewPager = view.findViewById(R.id.fragment_holder);
        mViewPager.setAdapter(new MenuFragmentAdapter(getChildFragmentManager(), mMenuScreenFragments));
        //mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        getActivity().setTitle(R.string.nav_drawer_launcher_grid);
        //getActivity().navigationView.getMenu().getItem(0).setChecked(true);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMenuScreenFragments.add(new GridFragment());
        mMenuScreenFragments.add(new ListFragment());
    }
}
