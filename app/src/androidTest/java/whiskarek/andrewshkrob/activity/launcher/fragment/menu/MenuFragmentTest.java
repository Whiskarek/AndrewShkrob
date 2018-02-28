package whiskarek.andrewshkrob.activity.launcher.fragment.menu;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.launcher.LauncherActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MenuFragmentTest {

    @Rule
    public ActivityTestRule<LauncherActivity> mActivityRule = new ActivityTestRule<>(
            LauncherActivity.class);

    @Before
    public void goToMenuFragment() {
        final Context context = mActivityRule.getActivity().getApplicationContext();
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean("pref_show_welcome_page_on_next_load", false).apply();
        onView(withId(R.id.launcher_screen)).perform(swipeUp());
    }

    /*@Test
    public void scrollViewPagerTest() {
        //Check if menu screen is displayed
        onView(allOf(withId(R.id.fragment_recycler_view_menu))).check(matches(isDisplayed()));
        //Try to swipe right
        onView(withId(R.id.launcher_screen)).perform(swipeRight());
        onView(allOf(withId(R.id.grid_item_main_view))).check(matches(isDisplayed()));

        //Try to swipe left
        onView(withId(R.id.launcher_screen)).perform(swipeRight());
        onView(allOf(withId(R.id.list_item_main_view))).check(matches(isDisplayed()));

        //Try to swipe left
        onView(withId(R.id.launcher_screen)).perform(swipeRight());
        //onView(withId(R.xml.preferences)).check(matches(isDisplayed()));

        //Try to swipe left
        onView(withId(R.id.launcher_screen)).perform(swipeRight());
        //onView(withId(R.xml.preferences)).check(matches(isDisplayed()));

    }*/

}
