package whiskarek.andrewshkrob.activity.welcomepage;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.launcher.LauncherActivity;
import whiskarek.andrewshkrob.activity.welcomepage.fragments.AboutFragment;
import whiskarek.andrewshkrob.activity.welcomepage.fragments.ModelTypeFragment;
import whiskarek.andrewshkrob.activity.welcomepage.fragments.ThemeFragment;
import whiskarek.andrewshkrob.activity.welcomepage.fragments.WelcomeFragment;
import whiskarek.andrewshkrob.view.adapter.WelcomePageFragmentAdapter;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class WelcomePageActivityTest {

    private WelcomePageActivity mWelcomePageActivity;

    @Before
    public void setUp() {
        mWelcomePageActivity = Robolectric
                .buildActivity(WelcomePageActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void scrollViewPagerTest() {
        //Check if there is 4 fragments inside viewpager
        final ViewPager viewPager = mWelcomePageActivity.findViewById(R.id.welcome_page_view_pager);
        final int screenAmount = viewPager.getAdapter().getCount();
        assertEquals(screenAmount, 4);

        //Check if the first screen is WelcomeFragment
        final boolean screen1 =
                 ((WelcomePageFragmentAdapter) viewPager.getAdapter())
                        .getItem(viewPager.getCurrentItem()) instanceof WelcomeFragment;
        assertEquals(true, screen1);

        //Swipe toLeft
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

        //Check if the second screen is AboutFragment
        final boolean screen2 =
                ((WelcomePageFragmentAdapter) viewPager.getAdapter())
                        .getItem(viewPager.getCurrentItem()) instanceof AboutFragment;
        assertEquals(true, screen2);

        //Swipe toLeft
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

        //Check if the third screen is ThemeFragment
        final boolean screen3 =
                ((WelcomePageFragmentAdapter) viewPager.getAdapter())
                        .getItem(viewPager.getCurrentItem()) instanceof ThemeFragment;
        assertEquals(true, screen3);

        //Swipe toLeft
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

        //Check if the 4th screen is ModelTypeFragment
        final boolean screen4 =
                ((WelcomePageFragmentAdapter) viewPager.getAdapter())
                        .getItem(viewPager.getCurrentItem()) instanceof ModelTypeFragment;
        assertEquals(true, screen4);
    }

    @Test
    public void fabTest() {
        final FloatingActionButton fab =
                mWelcomePageActivity.findViewById(R.id.welcome_page_fab_next);
        final ViewPager viewPager = mWelcomePageActivity.findViewById(R.id.welcome_page_view_pager);

        fab.performClick();

        //Check if the second screen is AboutFragment
        final boolean screen2 =
                ((WelcomePageFragmentAdapter) viewPager.getAdapter())
                        .getItem(viewPager.getCurrentItem()) instanceof AboutFragment;
        assertEquals(true, screen2);

        fab.performClick();

        //Check if the third screen is ThemeFragment
        final boolean screen3 =
                ((WelcomePageFragmentAdapter) viewPager.getAdapter())
                        .getItem(viewPager.getCurrentItem()) instanceof ThemeFragment;
        assertEquals(true, screen3);

        fab.performClick();

        //Check if the 4th screen is ModelTypeFragment
        final boolean screen4 =
                ((WelcomePageFragmentAdapter) viewPager.getAdapter())
                        .getItem(viewPager.getCurrentItem()) instanceof ModelTypeFragment;
        assertEquals(true, screen4);

        fab.performClick();

        //Check if LauncherActivity starts at the end
        final Intent intent = Shadows.shadowOf(mWelcomePageActivity).peekNextStartedActivity();
        assertEquals(
                LauncherActivity.class.getCanonicalName(),
                intent.getComponent().getClassName()
        );
    }

}
