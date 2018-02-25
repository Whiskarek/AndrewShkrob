package whiskarek.andrewshkrob.activities.launcher.fragment.menu;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class MenuScreenFragment extends Fragment {
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
}
