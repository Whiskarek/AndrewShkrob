package whiskarek.andrewshkrob.activity.main.fragments.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.main.fragments.LauncherFragment;
import whiskarek.andrewshkrob.activity.main.OffsetItemDecoration;

public class ListFragment extends LauncherFragment {

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_launcher, container, false);

        // SET UP RECYCLER VIEW
        //------------------------------------------------------------------------------------------
        mRecyclerView = view.findViewById(R.id.fragment_recycler_view_launcher);
        final int offset = getResources().getDimensionPixelOffset(R.dimen.offset);
        mRecyclerView.addItemDecoration(new OffsetItemDecoration(offset));

        // Create LinearLayoutManager
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        // Create ListAdapter
        final ListAdapter listAdapter = new ListAdapter(getContext());
        mRecyclerView.setAdapter(listAdapter);
        //------------------------------------------------------------------------------------------

        return view;
    }
}
