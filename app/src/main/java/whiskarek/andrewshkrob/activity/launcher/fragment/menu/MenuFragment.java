package whiskarek.andrewshkrob.activity.launcher.fragment.menu;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.LauncherApplication;
import whiskarek.andrewshkrob.LauncherExecutors;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.Sort;
import whiskarek.andrewshkrob.activity.launcher.LauncherActivity;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.grid.GridFragment;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.list.ListFragment;
import whiskarek.andrewshkrob.database.dao.ApplicationDao;
import whiskarek.andrewshkrob.database.entity.ApplicationEntity;
import whiskarek.andrewshkrob.view.SquareImageView;
import whiskarek.andrewshkrob.view.adapter.MenuViewPagerAdapter;
import whiskarek.andrewshkrob.viewmodel.ApplicationViewModel;

public class MenuFragment extends Fragment {

    private static List<Fragment> mMenuScreenFragments = new ArrayList<>();
    private ViewPager mViewPager = null;
    private LinearLayout mMostUsedApps = null;
    private ApplicationViewModel mViewModel = null;

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

        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getContext());

        final Boolean theme = sharedPreferences.getBoolean(getString(R.string.pref_key_theme_dark), false);

        mMostUsedApps = view.findViewById(R.id.menu_most_used_apps);

        if (theme) {
            mMostUsedApps.setBackgroundColor(Color.DKGRAY);
        } else {
            mMostUsedApps.setBackgroundColor(Color.WHITE);
        }

        return view;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mMenuScreenFragments.add(new GridFragment());
        mMenuScreenFragments.add(new ListFragment());

        mViewModel = ViewModelProviders.of(getActivity()).get(ApplicationViewModel.class);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel.getApplications().observe(this, new Observer<List<ApplicationEntity>>() {
            @Override
            public void onChanged(@Nullable final List<ApplicationEntity> appList) {
                if (appList == null) {
                    return;
                }

                final List<ApplicationEntity> apps = Sort.getMostUsed(appList);

                for (int i = 0; i < 5; i++) {
                    final LinearLayout appView = (LinearLayout) mMostUsedApps.getChildAt(i);

                    ((SquareImageView) appView.getChildAt(0)).setImageDrawable(apps.get(i).getIcon());
                    ((TextView) appView.getChildAt(1)).setText(apps.get(i).getLabel());

                    final int pos = i;
                    appView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(final View v) {
                            try {
                                startActivity(apps.get(pos).getIntent());

                                LauncherExecutors.getInstance().databaseIO().execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        final ApplicationDao appDao =
                                                ((LauncherApplication) getActivity().getApplicationContext())
                                                        .getDatabase()
                                                        .applicationDao();
                                        appDao.setLaunchAmount(
                                                apps.get(pos).getIntent(),
                                                apps.get(pos).getLaunchAmount() + 1
                                        );
                                    }
                                });
                            } catch (Exception e) {
                                Log.e("Launcher", e.toString());
                            }
                        }
                    });

                    appView.setOnCreateContextMenuListener(new ContextMenuListener(getContext(), apps.get(i)));
                }
            }
        });
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }


}
