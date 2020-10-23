package whiskarek.andrewshkrob.activity.launcher.fragment.menu;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import whiskarek.andrewshkrob.R;

public abstract class MenuScreenFragment extends Fragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    private RecyclerView mRecyclerView = null;
    protected View mRootLayout = null;

    protected void setRecyclerView(final RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    protected void setRootLayout(final View rootLayout) {
        mRootLayout = rootLayout;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setBackground(final Drawable drawable) {
        if (mRootLayout != null) {
            mRootLayout.setBackground(drawable);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getContext());

        final Boolean theme = sharedPreferences.getBoolean(getString(R.string.pref_key_theme_dark), false);

        if (theme) {
            view.setBackgroundColor(Color.DKGRAY);
        } else {
            view.setBackgroundColor(Color.WHITE);
        }
    }
}
