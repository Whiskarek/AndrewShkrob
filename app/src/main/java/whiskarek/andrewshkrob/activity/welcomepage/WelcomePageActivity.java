package whiskarek.andrewshkrob.activity.welcomepage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.BaseActivity;
import whiskarek.andrewshkrob.activity.launcher.LauncherActivity;
import whiskarek.andrewshkrob.activity.welcomepage.fragments.AboutFragment;
import whiskarek.andrewshkrob.activity.welcomepage.fragments.ModelTypeFragment;
import whiskarek.andrewshkrob.activity.welcomepage.fragments.ThemeFragment;
import whiskarek.andrewshkrob.activity.welcomepage.fragments.WelcomeFragment;
import whiskarek.andrewshkrob.database.LauncherDatabase;
import whiskarek.andrewshkrob.view.PageIndicatorView;
import whiskarek.andrewshkrob.view.adapter.WelcomePageFragmentAdapter;

public class WelcomePageActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    private PageIndicatorView mPageIndicator;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        mPageIndicator = findViewById(R.id.welcome_page_indicator);

        final List<Fragment> pages = new ArrayList<>();
        pages.add(new WelcomeFragment());
        pages.add(new AboutFragment());
        pages.add(new ThemeFragment());
        pages.add(new ModelTypeFragment());

        mViewPager = findViewById(R.id.welcome_page_view_pager);
        mViewPager.setAdapter(new WelcomePageFragmentAdapter(getSupportFragmentManager(), pages));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPageIndicator.setCurrentPage(position + 1);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mPageIndicator.setPageCount(pages.size());
        if (savedInstanceState != null) {
            mPageIndicator.setCurrentPage(savedInstanceState.getInt("cur_page", 1), false);
        } else {
            mPageIndicator.setCurrentPage(1, false);
        }

        final FloatingActionButton fab = findViewById(R.id.welcome_page_fab_next);
        fab.setOnClickListener(this);

        LauncherDatabase.getInstance(getApplicationContext());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v) {
        final int viewPagerTabNum = mViewPager.getCurrentItem();
        if (viewPagerTabNum < 3) {
            mViewPager.setCurrentItem(viewPagerTabNum + 1, true);
            mPageIndicator.setCurrentPage(mViewPager.getCurrentItem() + 1);
        } else if (viewPagerTabNum == 3) {
            final SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);

            sharedPreferences.edit()
                    .putBoolean(getString(R.string.pref_key_show_welcome_page_on_next_load), false)
                    .apply();
            startActivity(new Intent(this, LauncherActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        final int viewPagerTabNum = mViewPager.getCurrentItem();
        if (viewPagerTabNum > 0) {
            mViewPager.setCurrentItem(viewPagerTabNum - 1);
            mPageIndicator.setCurrentPage(mViewPager.getCurrentItem() + 1);
        }
    }
}
