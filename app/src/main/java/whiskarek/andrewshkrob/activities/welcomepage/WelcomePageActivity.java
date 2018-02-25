package whiskarek.andrewshkrob.activities.welcomepage;

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
import whiskarek.andrewshkrob.activities.BaseActivity;
import whiskarek.andrewshkrob.activities.welcomepage.fragments.AboutFragment;
import whiskarek.andrewshkrob.activities.welcomepage.fragments.ModelTypeFragment;
import whiskarek.andrewshkrob.activities.welcomepage.fragments.ThemeFragment;
import whiskarek.andrewshkrob.activities.welcomepage.fragments.WelcomeFragment;
import whiskarek.andrewshkrob.view.adapter.WelcomePageFragmentAdapter;

public class WelcomePageActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        final List<Fragment> pages = new ArrayList<>();
        pages.add(new WelcomeFragment());
        pages.add(new AboutFragment());
        pages.add(new ThemeFragment());
        pages.add(new ModelTypeFragment());

        mViewPager = findViewById(R.id.welcome_page_view_pager);
        mViewPager.setAdapter(new WelcomePageFragmentAdapter(getSupportFragmentManager(), pages));

        final FloatingActionButton fab = findViewById(R.id.welcome_page_fab_next);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        final int viewPagerTabNum = mViewPager.getCurrentItem();
        if (viewPagerTabNum < 3) {
            mViewPager.setCurrentItem(viewPagerTabNum + 1, true);
        } else if (viewPagerTabNum == 3) {
            final SharedPreferences sharedPreferences = PreferenceManager
                    .getDefaultSharedPreferences(this);

            sharedPreferences.edit()
                    .putBoolean(getString(R.string.pref_key_show_welcome_page_on_next_load), false)
                    .apply();
            //startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        final int viewPagerTabNum = mViewPager.getCurrentItem();
        if (viewPagerTabNum > 0) {
            mViewPager.setCurrentItem(viewPagerTabNum - 1);
        }
    }
}
