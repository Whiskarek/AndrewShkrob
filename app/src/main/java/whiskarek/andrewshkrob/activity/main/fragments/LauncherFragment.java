package whiskarek.andrewshkrob.activity.main.fragments;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class LauncherFragment extends Fragment {

    protected RecyclerView mRecyclerView = null;
    protected View mRootLayout = null;

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setBackground(final Drawable drawable) {
        if (mRootLayout != null) {
            mRootLayout.setBackground(drawable);
        }
    }
}
