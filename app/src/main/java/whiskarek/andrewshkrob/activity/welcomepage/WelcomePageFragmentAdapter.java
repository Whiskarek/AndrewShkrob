package whiskarek.andrewshkrob.activity.welcomepage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import whiskarek.andrewshkrob.activity.welcomepage.fragments.AboutFragment;
import whiskarek.andrewshkrob.activity.welcomepage.fragments.ModelTypeFragment;
import whiskarek.andrewshkrob.activity.welcomepage.fragments.ThemeFragment;
import whiskarek.andrewshkrob.activity.welcomepage.fragments.WelcomeFragment;

public class WelcomePageFragmentAdapter extends FragmentPagerAdapter {

    public WelcomePageFragmentAdapter(final FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(final int position) {
        switch (position) {
            case 0: {
                return new WelcomeFragment();
            }
            case 1: {
                return new AboutFragment();
            }
            case 2: {
                return new ThemeFragment();
            }
            case 3: {
                return new ModelTypeFragment();
            }
        }
        return null;
    }
}
