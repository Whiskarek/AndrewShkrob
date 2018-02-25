package whiskarek.andrewshkrob.activities.launcher;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.view.VerticalViewPager;

public class ScreenChangeListener implements NavigationView.OnNavigationItemSelectedListener {

    private static int mCurrentId = R.id.nav_drawer_launcher_desktop;
    private final DrawerLayout mDrawer;
    private final VerticalViewPager mViewPager;
    private final Toolbar mToolbar;

    public ScreenChangeListener(final DrawerLayout drawer, final VerticalViewPager viewPager,
                                final Toolbar toolbar, final ActionBarDrawerToggle toggle) {
        mDrawer = drawer;
        mViewPager = viewPager;
        mToolbar = toolbar;

        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
        final int id = item.getItemId();

        if (mCurrentId != id) {
            if (id == R.id.nav_drawer_launcher_desktop) {
                mViewPager.setCurrentItem(1);
                item.setChecked(true);
                mToolbar.setTitle(item.getTitle());
            } else if (id == R.id.nav_drawer_launcher_grid) {
                mViewPager.setCurrentItem(1);
                item.setChecked(true);
                mToolbar.setTitle(item.getTitle());
            } else if (id == R.id.nav_drawer_launcher_list) {
                mViewPager.setCurrentItem(1);
                item.setChecked(true);
                mToolbar.setTitle(item.getTitle());
            } else if (id == R.id.nav_drawer_settings) {

                item.setChecked(true);
                mToolbar.setTitle(item.getTitle());
                //startActivity(new Intent(this, SettingsActivity.class));
                //finish();
            }
            mCurrentId = id;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
