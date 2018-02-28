package whiskarek.andrewshkrob.activity.launcher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.launcher.fragment.desktop.DesktopFragment;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.MenuFragment;
import whiskarek.andrewshkrob.view.VerticalViewPager;
import whiskarek.andrewshkrob.view.adapter.VerticalViewPagerAdapter;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class LauncherActivityTest {

    private LauncherActivity mLauncherActivity;

    @Before
    public void setUp() {
        mLauncherActivity = Robolectric
                .buildActivity(LauncherActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void verticalViewPagerTest() {
        final VerticalViewPager viewPager = mLauncherActivity.findViewById(R.id.launcher_screen);

        //Check if the first screen is Desktop
        final boolean screen1 =
                ((VerticalViewPagerAdapter) viewPager.getAdapter())
                        .getItem(viewPager.getCurrentItem()) instanceof DesktopFragment;
        assertEquals(true, screen1);

        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);

        //Check if the second screen is Menu
        final boolean screen2 =
                ((VerticalViewPagerAdapter) viewPager.getAdapter())
                        .getItem(viewPager.getCurrentItem()) instanceof MenuFragment;
        assertEquals(true, screen2);
    }

}
