package whiskarek.andrewshkrob.activity.launcher.fragment.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.launcher.LauncherActivity;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.grid.GridFragment;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.list.ListFragment;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.settings.SettingsFragment;
import whiskarek.andrewshkrob.view.adapter.MenuFragmentAdapter;

public class MenuFragment extends Fragment {

    private static List<Fragment> mMenuScreenFragments = new ArrayList<>();
    private ViewPager mViewPager = null;

    private final ViewPager.SimpleOnPageChangeListener mOnPageChangeListener =
            new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0: {
                            getActivity().setTitle(R.string.nav_drawer_grid);
                            ((LauncherActivity) getActivity()).getNavigationView()
                                    .setCheckedItem(R.id.nav_drawer_grid);
                            break;
                        }
                        case 1: {
                            getActivity().setTitle(R.string.nav_drawer_list);
                            ((LauncherActivity) getActivity()).getNavigationView()
                                    .setCheckedItem(R.id.nav_drawer_list);
                            break;
                        }
                        case 2: {
                            getActivity().setTitle(R.string.nav_drawer_settings);
                            ((LauncherActivity) getActivity()).getNavigationView()
                                    .setCheckedItem(R.id.nav_drawer_settings);
                        }
                    }
                }
            };

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menu, container, false);

        Log.d("111", "onCreateView");
        mViewPager = view.findViewById(R.id.fragment_holder);
        mViewPager.setAdapter(new MenuFragmentAdapter(
                getFragmentManager(),
                mMenuScreenFragments
        ));
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        Log.d("111", "ViewPager created: " + (mViewPager == null ? "null" : "not null"));
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mMenuScreenFragments.add(new GridFragment());
        mMenuScreenFragments.add(new ListFragment());
        mMenuScreenFragments.add(new SettingsFragment());
        Log.d("111", "onCreate");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("111", "onViewCreated");
    }

    public ViewPager getViewPager() {
        Log.d("111", "getViewPager(): " + (mViewPager == null ? "null" : "not null"));
        return mViewPager;
    }


}