package whiskarek.andrewshkrob.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class VerticalViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments;

    public VerticalViewPagerAdapter(final FragmentManager fragmentManager,
                                    final List<Fragment> fragments) {
        super(fragmentManager);
        mFragments = fragments;
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(final int position) {
        if (position >= 0 && position < mFragments.size()) {
            return mFragments.get(position);
        }

        return null;
    }

}
