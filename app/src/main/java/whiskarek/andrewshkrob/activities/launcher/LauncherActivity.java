package whiskarek.andrewshkrob.activities.launcher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.crashlytics.android.Crashlytics;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activities.BaseActivity;
import whiskarek.andrewshkrob.activities.ProfileActivity;
import whiskarek.andrewshkrob.activities.launcher.fragment.desktop.DesktopFragment;
import whiskarek.andrewshkrob.activities.launcher.fragment.menu.MenuFragment;
import whiskarek.andrewshkrob.activities.welcomepage.WelcomePageActivity;
import whiskarek.andrewshkrob.view.VerticalViewPager;
import whiskarek.andrewshkrob.view.adapter.VerticalViewPagerAdapter;

public class LauncherActivity extends BaseActivity {

    private NavigationView mNavigationView = null;

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

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        final List<Fragment> screens = new ArrayList<>();
        screens.add(new DesktopFragment());
        screens.add(new MenuFragment());
        final VerticalViewPager viewPager = findViewById(R.id.vertical_view_pager);
        final VerticalViewPagerAdapter verticalViewPagerAdapter =
                new VerticalViewPagerAdapter(getSupportFragmentManager(), screens);
        viewPager.setAdapter(verticalViewPagerAdapter);

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(
                new ScreenChangeListener(
                        (DrawerLayout) findViewById(R.id.drawer_layout),
                        viewPager,
                        toolbar,
                        toggle));
        mNavigationView.setCheckedItem(R.id.nav_drawer_launcher_desktop);
        setTitle(R.string.nav_drawer_launcher_desktop);
        mNavigationView.getHeaderView(0)
                .findViewById(R.id.navigation_header_photo)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(LauncherActivity.this, ProfileActivity.class)
                );
            }
        });

        checkForUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkForCrashes();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterManagers();
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
}
