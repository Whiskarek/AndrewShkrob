package whiskarek.andrewshkrob.activity.welcomepage.fragments;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertNotNull;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

@RunWith(RobolectricTestRunner.class)
public class AboutFragmentTest {

    private AboutFragment mAboutFragment;

    @Before
    public void setUp() {
        mAboutFragment = new AboutFragment();
        startFragment(mAboutFragment);
    }

    @Test
    public void aboutFragmentNotNull() {
        assertNotNull(mAboutFragment);
    }

    @Test
    public void aboutFragmentViewNotNull() {
        assertNotNull(mAboutFragment.getView());
    }

}