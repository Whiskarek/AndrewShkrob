package whiskarek.andrewshkrob.activity.launcher.fragment.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.launcher.LauncherActivity;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.grid.GridFragment;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.list.ListFragment;
import whiskarek.andrewshkrob.view.adapter.MenuViewPagerAdapter;

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
                            ((LauncherActivity) getActivity()).setCurrentId(R.id.nav_drawer_grid);
                            break;
                        }
                        case 1: {
                            getActivity().setTitle(R.string.nav_drawer_list);
                            ((LauncherActivity) getActivity()).getNavigationView()
                                    .setCheckedItem(R.id.nav_drawer_list);
                            ((LauncherActivity) getActivity()).setCurrentId(R.id.nav_drawer_list);
                            break;
                        }
                    }
                }
            };

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menu, container, false);

        mViewPager = view.findViewById(R.id.fragment_holder);
        mViewPager.setAdapter(new MenuViewPagerAdapter(
                getFragmentManager(),
                mMenuScreenFragments
        ));
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setPageTransformer(true, new RotateUpTransformer());
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN && v instanceof ViewGroup) {
                    ((ViewGroup) v).requestDisallowInterceptTouchEvent(true);
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mMenuScreenFragments.add(new GridFragment());
        mMenuScreenFragments.add(new ListFragment());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }


}
