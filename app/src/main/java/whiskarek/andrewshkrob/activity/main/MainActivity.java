package whiskarek.andrewshkrob.activity.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.ProfileActivity;
import whiskarek.andrewshkrob.activity.main.fragments.LauncherFragment;
import whiskarek.andrewshkrob.activity.SettingsActivity;
import whiskarek.andrewshkrob.activity.main.fragments.grid.GridFragment;
import whiskarek.andrewshkrob.activity.main.fragments.list.ListFragment;
import whiskarek.andrewshkrob.activity.welcomepage.WelcomePageActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int currentId = 0;
    private ApplicationsLoader mAppLoader = null;
    private ViewPager mViewPager = null;

    private final LoaderManager.LoaderCallbacks<List<Application>> mLoader =
            new LoaderManager.LoaderCallbacks<List<Application>>() {
        @Override
        public Loader<List<Application>> onCreateLoader(int id, Bundle args) {
            mAppLoader = new ApplicationsLoader(MainActivity.this);
            return mAppLoader;
        }

        @Override
        public void onLoadFinished(Loader<List<Application>> loader, List<Application> data) {
            Log.i("BROADCAST", "Loading finished");
            final LauncherFragment fragmentGrid = ((LauncherAdapter) mViewPager.getAdapter()).getItem(0);
            final LauncherFragment fragmentList = ((LauncherAdapter) mViewPager.getAdapter()).getItem(1);
            fragmentGrid.setData(data);
            fragmentList.setData(data);
        }

        @Override
        public void onLoaderReset(Loader<List<Application>> loader) {
            Log.i("BROADCAST", "Loading reset");
            final LauncherFragment fragment = ((LauncherAdapter) mViewPager.getAdapter()).getItem(mViewPager.getCurrentItem());
            final LauncherFragment fragmentGrid = ((LauncherAdapter) mViewPager.getAdapter()).getItem(0);
            final LauncherFragment fragmentList = ((LauncherAdapter) mViewPager.getAdapter()).getItem(1);
            fragmentGrid.setData(null);
            fragmentList.setData(null);
        }
    };

    private final ViewPager.SimpleOnPageChangeListener mOnPageListener = new ViewPager.SimpleOnPageChangeListener() {
        @Override
        public void onPageSelected(final int position) {
            switch (position) {
                case 0: {
                    MainActivity.this.setTitle(R.string.nav_drawer_launcher_grid);
                    break;
                }
                case 1: {
                    MainActivity.this.setTitle(R.string.nav_drawer_launcher_list);
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        //
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        startActivity(new Intent(this, ProfileActivity.class));

        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        final boolean launchWelcome = sharedPreferences.getBoolean(
                getString(R.string.pref_key_show_welcome_page_on_next_load), true);
        if (launchWelcome) {
            startActivity(new Intent(this, WelcomePageActivity.class));
            finish();
        }

        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final List<LauncherFragment> launcherFragments = new ArrayList<>();
        launcherFragments.add(new GridFragment());
        launcherFragments.add(new ListFragment());

        setTitle(R.string.nav_drawer_launcher_grid);

        mViewPager = (ViewPager) findViewById(R.id.fragment_holder);
        mViewPager.setAdapter(new LauncherAdapter(getSupportFragmentManager(), launcherFragments));
        mViewPager.addOnPageChangeListener(mOnPageListener);
        getSupportLoaderManager().initLoader(1, null, mLoader);

        checkForUpdates();
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
            setTitle(savedInstanceState.getString("title_name", getString(R.string.app_name)));
            currentId = savedInstanceState.getInt("current_id", 0);
        }
    }

    @Override
    public void onBackPressed() {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
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

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        if (themeDark == true) {
            theme.applyStyle(R.style.AppThemeDark, true);
        } else {
            theme.applyStyle(R.style.AppThemeLight, true);
        }

        return theme;
    }

    @Override
    public void onResume() {
        super.onResume();

        checkForCrashes();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterManagers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unregisterManagers();
    }

    private void checkForCrashes() {
        CrashManager.register(this);
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register(this);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }
}
