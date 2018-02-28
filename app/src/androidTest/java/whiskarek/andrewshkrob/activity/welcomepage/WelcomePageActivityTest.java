package whiskarek.andrewshkrob.activity.welcomepage;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import whiskarek.andrewshkrob.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.action.ViewActions.swipeRight;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class WelcomePageActivityTest {

    @Rule
    public ActivityTestRule<WelcomePageActivity> mActivityRule = new ActivityTestRule<>(
            WelcomePageActivity.class);

    @Test
    public void scrollViewPagerTest() {
        // First screen
        onView(withId(R.id.circle_image_view)).check(matches(isDisplayed()));

        // Try to swipe right
        onView(withId(R.id.welcome_page_view_pager)).perform(swipeRight());
        onView(withId(R.id.circle_image_view)).check(matches(isDisplayed()));

        // Second screen
        onView(withId(R.id.welcome_page_view_pager)).perform(swipeLeft());
        onView(withId(R.id.about_description)).check(matches(isDisplayed()));

        // Third screen
        onView(withId(R.id.welcome_page_view_pager)).perform(swipeLeft());
        onView(withId(R.id.radio_group_theme)).check(matches(isDisplayed()));

        // Fourth screen
        onView(withId(R.id.welcome_page_view_pager)).perform(swipeLeft());
        onView(withId(R.id.radio_group_model_type)).check(matches(isDisplayed()));

        // Try to swipe next
        onView(withId(R.id.welcome_page_view_pager)).perform(swipeLeft());
        onView(withId(R.id.radio_group_model_type)).check(matches(isDisplayed()));
    }

    @Test
    public void fabTest() {

        // Second screen
        onView(withId(R.id.welcome_page_fab_next)).perform(click());
        onView(withId(R.id.about_description)).check(matches(isDisplayed()));

        // Third screen
        onView(withId(R.id.welcome_page_fab_next)).perform(click());
        onView(withId(R.id.radio_group_theme)).check(matches(isDisplayed()));

        // Fourth screen
        onView(withId(R.id.welcome_page_fab_next)).perform(click());
        onView(withId(R.id.radio_group_model_type)).check(matches(isDisplayed()));

        // Launcher screen
        onView(withId(R.id.welcome_page_fab_next)).perform(click());
        onView(withId(R.id.launcher_screen)).check(matches(isDisplayed()));
    }

    @Test
    public void buttonBackTest() {
        // Check at first screen
        Espresso.pressBack();
        onView(withId(R.id.circle_image_view)).check(matches(isDisplayed()));

        // Go to last screen
        onView(withId(R.id.welcome_page_fab_next)).perform(click());
        onView(withId(R.id.welcome_page_fab_next)).perform(click());
        onView(withId(R.id.welcome_page_fab_next)).perform(click());

        // Go to first and check every screen
        Espresso.pressBack();
        onView(withId(R.id.radio_group_theme)).check(matches(isDisplayed()));

        Espresso.pressBack();
        onView(withId(R.id.about_description)).check(matches(isDisplayed()));

        Espresso.pressBack();
        onView(withId(R.id.circle_image_view)).check(matches(isDisplayed()));
    }

}
