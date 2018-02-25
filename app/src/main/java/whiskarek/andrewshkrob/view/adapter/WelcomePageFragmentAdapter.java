package whiskarek.andrewshkrob.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class WelcomePageFragmentAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mPages;

    public WelcomePageFragmentAdapter(final FragmentManager fragmentManager,
                                      final List<Fragment> pages) {
        super(fragmentManager);
        mPages = pages;
    }

    @Override
    public int getCount() {
        return mPages.size();
    }

    @Override
    public Fragment getItem(final int position)
    {
        return mPages.get(position);
    }
}
