package whiskarek.andrewshkrob.activity.launcher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.crashlytics.android.Crashlytics;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.BaseActivity;
import whiskarek.andrewshkrob.activity.ProfileActivity;
import whiskarek.andrewshkrob.activity.launcher.fragment.desktop.DesktopFragment;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.MenuFragment;
import whiskarek.andrewshkrob.activity.settings.SettingsActivity;
import whiskarek.andrewshkrob.activity.welcomepage.WelcomePageActivity;
import whiskarek.andrewshkrob.background.ApplicationManager;
import whiskarek.andrewshkrob.view.viewpager.VerticalViewPager;
import whiskarek.andrewshkrob.view.adapter.VerticalViewPagerAdapter;

public class LauncherActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        VerticalViewPager.OnPageChangeListener,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static int mCurrentId = R.id.nav_drawer_desktop;

    private NavigationView mNavigationView = null;
    private VerticalViewPagerAdapter mVerticalViewPagerAdapter = null;
    private VerticalViewPager mViewPager = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(getApplicationContext(), new Crashlytics());

        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        final boolean launchWelcome = sharedPreferences.getBoolean(
                getString(R.string.pref_key_show_welcome_page_on_next_load), true);
        if (launchWelcome) {
            startActivity(new Intent(this, WelcomePageActivity.class));
            finish();
        }

        setContentView(R.layout.activity_launcher);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final List<Fragment> screen = new ArrayList<>();
        screen.add(new DesktopFragment());
        screen.add(new MenuFragment());

        mVerticalViewPagerAdapter =
                new VerticalViewPagerAdapter(getSupportFragmentManager(), screen);

        mViewPager = findViewById(R.id.launcher_screen);
        mViewPager.setAdapter(mVerticalViewPagerAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(1);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setCheckedItem(R.id.nav_drawer_desktop);
        setTitle(R.string.nav_drawer_desktop);
        mNavigationView.getHeaderView(0)
                .findViewById(R.id.navigation_header_photo)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(
                                LauncherActivity.this,
                                ProfileActivity.class
                        ));
                    }
                });

        Log.d("Launcher", "Starting Service");
        startService(new Intent(this, ApplicationManager.class));

        checkForUpdates();
    }

    public NavigationView getNavigationView() {
        return mNavigationView;
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title_name", getTitle().toString());
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            final String title = savedInstanceState
                    .getString("title_name", getString(R.string.app_name));
            setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForCrashes();
    }

    @Override
    protected void onStart() {
        super.onStart();
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterManagers();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        stopService(new Intent(this, ApplicationManager.class));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        final int id = item.getItemId();

        if (mCurrentId != id) {
            if (id == R.id.nav_drawer_desktop) {
                mViewPager.setCurrentItem(0);
                item.setChecked(true);
                setTitle(item.getTitle());
                mCurrentId = id;
            } else if (id == R.id.nav_drawer_grid) {
                mViewPager.setCurrentItem(1);
                final MenuFragment menuFragment =
                        (MenuFragment) mVerticalViewPagerAdapter.getItem(1);
                menuFragment.getViewPager().setCurrentItem(0);
                item.setChecked(true);
                setTitle(item.getTitle());
                mCurrentId = id;
            } else if (id == R.id.nav_drawer_list) {
                mViewPager.setCurrentItem(1);
                final MenuFragment menuFragment =
                        (MenuFragment) mVerticalViewPagerAdapter.getItem(1);
                menuFragment.getViewPager().setCurrentItem(1);
                item.setChecked(true);
                setTitle(item.getTitle());
                mCurrentId = id;
            } else if (id == R.id.nav_drawer_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
            }
        }

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void checkForCrashes() {
        CrashManager.register(getApplicationContext());
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(LauncherActivity.this);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }

    @Override
    public void onPageScrolled(final int position, final float positionOffset,
                               final int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(final int position) {
        switch (position) {
            case 0: {
                LauncherActivity.this.setTitle(R.string.nav_drawer_desktop);
                mNavigationView.setCheckedItem(R.id.nav_drawer_desktop);
                break;
            }
            case 1: {
                // TODO
                /*
                final MenuFragment menuFragment =
                        (MenuFragment) mVerticalViewPagerAdapter.getItem(1);
                switch (menuFragment.getViewPager().getCurrentItem()) {
                    case 0: {
                        setTitle(R.string.nav_drawer_grid);
                        mNavigationView.setCheckedItem(R.id.nav_drawer_grid);
                        break;
                    }
                    case 1: {
                        setTitle(R.string.nav_drawer_list);
                        mNavigationView.setCheckedItem(R.id.nav_drawer_list);
                        break;
                    }
                }
                */
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(final int state) {

    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences,
                                          final String key) {
        if (key.equals(getResources().getString(R.string.pref_key_theme_dark))) {
            recreate();
        }
    }

    public void setCurrentId(final int id) {
        mCurrentId = id;
    }
}
