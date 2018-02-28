package whiskarek.andrewshkrob.activity.welcomepage.fragments;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
public class WelcomeFragmentTest {

    private WelcomeFragment mWelcomeFragment;

    @Before
    public void setUp() {
        mWelcomeFragment = new WelcomeFragment();
        startFragment(mWelcomeFragment);
    }

    @Test
    public void welcomeFragmentNotNull() {
        assertNotNull(mWelcomeFragment);
    }

    @Test
    public void welcomeFragmentViewNotNull() {
        assertNotNull(mWelcomeFragment.getView());
    }

}
