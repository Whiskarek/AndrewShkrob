package whiskarek.andrewshkrob.activity.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.ProfileActivity;
import whiskarek.andrewshkrob.activity.SettingsActivity;
import whiskarek.andrewshkrob.activity.main.fragments.LauncherFragment;
import whiskarek.andrewshkrob.activity.main.fragments.grid.GridFragment;
import whiskarek.andrewshkrob.activity.main.fragments.list.ListFragment;
import whiskarek.andrewshkrob.activity.welcomepage.WelcomePageActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private int currentId = 0;
    private List<LauncherFragment> mLauncherFragments = new ArrayList<>();
    private NavigationView navigationView = null;
    private final ViewPager.SimpleOnPageChangeListener mOnPageChangeListener =
            new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0: {
                            MainActivity.this.setTitle(R.string.nav_drawer_launcher_grid);
                            navigationView.getMenu().getItem(0).setChecked(true);
                            break;
                        }
                        case 1: {
                            MainActivity.this.setTitle(R.string.nav_drawer_launcher_list);
                            navigationView.getMenu().getItem(1).setChecked(true);
                        }
                    }
                }
            };
    private ViewPager mViewPager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new MainActivityObserver(getLifecycle(), this);

        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        final boolean launchWelcome = sharedPreferences.getBoolean(
                getString(R.string.pref_key_show_welcome_page_on_next_load), true);
        if (launchWelcome) {
            startActivity(new Intent(this, WelcomePageActivity.class));
            finish();
        }

        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getHeaderView(0)
                .findViewById(R.id.navigation_header_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        mLauncherFragments.add(new GridFragment());
        mLauncherFragments.add(new ListFragment());

        mViewPager = findViewById(R.id.fragment_holder);
        mViewPager.setAdapter(new LauncherAdapter(getSupportFragmentManager(), mLauncherFragments));
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        setTitle(R.string.nav_drawer_launcher_grid);
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title_name", getTitle().toString());
        outState.putInt("current_id", currentId);
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            final String title = savedInstanceState
                    .getString("title_name", getString(R.string.app_name));
            setTitle(title);
            currentId = savedInstanceState.getInt("current_id", 0);
        }
    }

    @Override
    public void onBackPressed() {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        final int id = item.getItemId();

        if (currentId != id) {
            if (id == R.id.nav_drawer_launcher_grid) {
                mViewPager.setCurrentItem(0);
                item.setChecked(true);
                setTitle(item.getTitle());
            } else if (id == R.id.nav_drawer_launcher_list) {
                mViewPager.setCurrentItem(1);
                item.setChecked(true);
                setTitle(item.getTitle());
            } else if (id == R.id.nav_drawer_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                finish();
            }
            currentId = id;
        }

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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