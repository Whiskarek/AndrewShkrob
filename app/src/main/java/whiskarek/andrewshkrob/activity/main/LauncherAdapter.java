package whiskarek.andrewshkrob.activity.main;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import whiskarek.andrewshkrob.activity.main.fragments.LauncherFragment;

public class LauncherAdapter extends FragmentPagerAdapter {

    private final List<LauncherFragment> mFragments;

    public LauncherAdapter(final FragmentManager fragmentManager, final List<LauncherFragment> fragments) {
        super(fragmentManager);
        mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public LauncherFragment getItem(final int position) {
        if (position >= 0 && position < mFragments.size()) {
            return mFragments.get(position);
        }

        return null;
    }
}
