package whiskarek.andrewshkrob.activity.welcomepage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yandex.metrica.YandexMetrica;

import whiskarek.andrewshkrob.LauncherApplication;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.main.MainActivity;

public class WelcomePageActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        YandexMetrica.reportEvent("WelcomePageActivity","onCreate");

        mViewPager = findViewById(R.id.welcome_page_view_pager);
        mViewPager.setAdapter(new WelcomePageFragmentAdapter(getSupportFragmentManager()));

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
            startActivity(new Intent(this, MainActivity.class));
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

    @Override
    public Resources.Theme getTheme() {
        final Resources.Theme theme = super.getTheme();
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        final boolean themeDark =
                sharedPreferences.getBoolean(getString(R.string.pref_key_theme_dark), false);
        if (themeDark) {
            theme.applyStyle(R.style.AppThemeDark, true);
        } else {
            theme.applyStyle(R.style.AppThemeLight, true);
        }

        return theme;
    }
}
