package whiskarek.andrewshkrob.activity.main;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.crashlytics.android.Crashlytics;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import whiskarek.andrewshkrob.Application;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.Utils;
import whiskarek.andrewshkrob.activity.ProfileActivity;
import whiskarek.andrewshkrob.activity.SettingsActivity;
import whiskarek.andrewshkrob.activity.database.ApplicationDatabaseHelper;
import whiskarek.andrewshkrob.activity.main.fragments.LauncherFragment;
import whiskarek.andrewshkrob.activity.main.fragments.grid.GridFragment;
import whiskarek.andrewshkrob.activity.main.fragments.list.ListFragment;
import whiskarek.andrewshkrob.activity.welcomepage.WelcomePageActivity;
import whiskarek.andrewshkrob.broadcast.ApplicationChangesReceiver;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int currentId = 0;
    private List<LauncherFragment> mLauncherFragments = new ArrayList<>();
    private NavigationView navigationView = null;
    private ViewPager mViewPager;
    private final ApplicationChangesReceiver mAppChangesReceiver =
            new ApplicationChangesReceiver(this);
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

    private static ApplicationDatabaseHelper mDatabase = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

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
        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });

        mDatabase = new ApplicationDatabaseHelper(this);

        if (Utils.mApps == null) {
            Utils.mApps = Utils.loadApplications(this, mDatabase);
        }

        mLauncherFragments.add(new GridFragment());
        mLauncherFragments.add(new ListFragment());

        mViewPager = findViewById(R.id.fragment_holder);
        mViewPager.setAdapter(new LauncherAdapter(getSupportFragmentManager(), mLauncherFragments));
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        setTitle(R.string.nav_drawer_launcher_grid);
        navigationView.getMenu().getItem(0).setChecked(true);

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

    @Contract(pure = true)
    public static List<Application> getApps() {
        return Utils.mApps;
    }

    @Contract(pure = true)
    public static ApplicationDatabaseHelper getDatabase() {
        return mDatabase;
    }

    public void addApp(final Intent intent) {
        try {
            final String packageName = intent.getData().getSchemeSpecificPart();
            final Intent data = new Intent();
            data.setPackage(packageName);
            data.addCategory(Intent.CATEGORY_LAUNCHER);
            ResolveInfo appInfo = this.getPackageManager().resolveActivity(data, 0);

            Application app = Utils.getApp(appInfo, this);

            Utils.mApps.add(0, app);

            Utils.mApps = Utils.sortApps(Utils.mApps, this);

            mLauncherFragments.get(0).getRecyclerView().getAdapter().notifyDataSetChanged();
            mLauncherFragments.get(1).getRecyclerView().getAdapter().notifyDataSetChanged();

            List<Application> apps = new ArrayList<>();
            apps.add(app);
            mDatabase.addToDatabase(apps);
        } catch (NullPointerException e) {
            Log.e("INSTALL_APP", e.toString());
        }
    }

    public void deleteApp(final Intent intent) {
        mDatabase.clearData();
        try {
            final String packageName = intent.getData().getSchemeSpecificPart();

            for (int i = 0; i < Utils.mApps.size(); i++) {
                final Application application = Utils.mApps.get(i);
                if (application.getPackageName().equals(packageName)) {
                    Utils.mApps.remove(i);
                    mLauncherFragments.get(0).getRecyclerView().getAdapter().notifyItemRemoved(i);
                    mLauncherFragments.get(1).getRecyclerView().getAdapter().notifyItemRemoved(i);
                    break;
                }
            }
        } catch (NullPointerException e) {
            Log.e("DELETE_APP", e.toString());
        }
        mDatabase.addToDatabase(Utils.mApps);
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

    @Override
    public void onResume() {
        super.onResume();

        final IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");

        registerReceiver(mAppChangesReceiver, filter);

        checkForCrashes();
    }

    @Override
    public void onPause() {
        super.onPause();

        unregisterReceiver(mAppChangesReceiver);

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