package whiskarek.andrewshkrob.activity.main.fragments;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

public abstract class LauncherFragment extends Fragment {

    protected RecyclerView mRecyclerView = null;

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
