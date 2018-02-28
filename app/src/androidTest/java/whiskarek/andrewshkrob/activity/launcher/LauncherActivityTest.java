package whiskarek.andrewshkrob.activity.launcher;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import whiskarek.andrewshkrob.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class LauncherActivityTest {

    @Rule
    public ActivityTestRule<LauncherActivity> mActivityRule = new ActivityTestRule<>(
            LauncherActivity.class);

    @Test
    public void firstLaunchTest() {
        final Context context = InstrumentationRegistry.getTargetContext();
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean("pref_show_welcome_page_on_next_load", true).apply();
        onView(withId(R.id.circle_image_view)).check(matches(isDisplayed()));
    }

    @Test
    public void scrollVerticalViewPagerTest() {

        onView(withId(R.id.launcher_screen)).perform(swipeUp());
        onView(withId(R.id.fragment_holder)).check(matches(isDisplayed()));

        // TODO
        onView(withId(R.id.launcher_screen)).perform(swipeDown());
    }

}
