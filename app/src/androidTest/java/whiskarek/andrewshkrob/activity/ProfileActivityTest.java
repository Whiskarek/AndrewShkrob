package whiskarek.andrewshkrob.activity;

import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;

public class ProfileActivityTest {

    @Rule
    public ActivityTestRule<ProfileActivity> mActivityRule = new ActivityTestRule<>(
            ProfileActivity.class);
}
