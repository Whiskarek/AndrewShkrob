package whiskarek.andrewshkrob.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.Settings;
import whiskarek.andrewshkrob.activity.launcher.LauncherActivity;
import whiskarek.andrewshkrob.fragments.AboutFragment;
import whiskarek.andrewshkrob.fragments.SettingsFragment;
import whiskarek.andrewshkrob.fragments.ThemeFragment;
import whiskarek.andrewshkrob.fragments.WelcomeFragment;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private WelcomeFragment mWelcomeFragment;
    private AboutFragment mAboutFragment;
    private ThemeFragment mThemeFragment;
    private SettingsFragment mSettingsFragment;
    private FragmentTransaction mTransaction;

    private int mScreen = 1;
    private boolean mActivityStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (mWelcomeFragment == null) {
            mWelcomeFragment = new WelcomeFragment();
        }
        if (mAboutFragment == null) {
            mAboutFragment = new AboutFragment();
        }
        if (mThemeFragment == null) {
            mThemeFragment = new ThemeFragment();
        }
        if (mSettingsFragment == null) {
            mSettingsFragment = new SettingsFragment();
        }

        if (savedInstanceState != null) {
            mScreen = savedInstanceState.getInt("mScreen");
            mActivityStarted = savedInstanceState.getBoolean("mActivityStarted");
        }

        if (!mActivityStarted) {
            mTransaction = getFragmentManager().beginTransaction();

            mTransaction.add(R.id.fragmentWelcomeActivity, mWelcomeFragment);
            mTransaction.commit();
            mActivityStarted = true;
        }
        Button btNext = (Button) findViewById(R.id.btNext);
        btNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        mScreen++;

        switch (mScreen) {
            case 1: {
                mTransaction = getFragmentManager().beginTransaction();
                mTransaction.replace(R.id.fragmentWelcomeActivity, mWelcomeFragment);
                mTransaction.addToBackStack(null);
                mTransaction.commit();
                break;
            }
            case 2: {
                mTransaction = getFragmentManager().beginTransaction();
                mTransaction.replace(R.id.fragmentWelcomeActivity, mAboutFragment);
                mTransaction.addToBackStack(null);
                mTransaction.commit();
                break;
            }
            case 3: {
                mTransaction = getFragmentManager().beginTransaction();
                mTransaction.replace(R.id.fragmentWelcomeActivity, mThemeFragment);
                mTransaction.addToBackStack(null);
                mTransaction.commit();
                break;
            }
            case 4: {
                mTransaction = getFragmentManager().beginTransaction();
                mTransaction.replace(R.id.fragmentWelcomeActivity, mSettingsFragment);
                mTransaction.addToBackStack(null);
                mTransaction.commit();
                break;
            }
        }

        if (mScreen < 1) {
            mScreen = 1;
        }

        if (mScreen > 4) {
            Settings.setAppWasLaunchedEarlier(true);
            Settings.saveSettings(this, this);
            startActivity(new Intent(this, LauncherActivity.class));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("mScreen", mScreen);
        outState.putBoolean("mActivityStarted", mActivityStarted);
    }

    @Override
    public void onBackPressed() {
        mScreen--;

        if (!(mScreen < 1)) {
            super.onBackPressed();
        } else {
            mScreen = 1;
            super.onBackPressed();
        }
    }
}
