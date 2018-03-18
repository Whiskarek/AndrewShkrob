package whiskarek.andrewshkrob.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MenuViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments;

    public MenuViewPagerAdapter(final FragmentManager fragmentManager,
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
        return mFragments.get(position);
    }

}
