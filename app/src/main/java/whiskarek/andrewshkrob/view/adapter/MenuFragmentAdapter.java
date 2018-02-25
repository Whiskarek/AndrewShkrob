package whiskarek.andrewshkrob.view.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import whiskarek.andrewshkrob.activities.launcher.fragment.menu.MenuScreenFragment;

public class MenuFragmentAdapter extends FragmentPagerAdapter {

    private final List<MenuScreenFragment> mFragments;

    public MenuFragmentAdapter(final FragmentManager fragmentManager,
                               final List<MenuScreenFragment> fragments) {
        super(fragmentManager);
        mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public MenuScreenFragment getItem(final int position) {
        if (position >= 0 && position < mFragments.size()) {
            return mFragments.get(position);
        }

        return null;
    }

}
