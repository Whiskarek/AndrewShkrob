package whiskarek.andrewshkrob.activity.main.fragments.list;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.Application;
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
        mRecyclerView =
                (RecyclerView) view.findViewById(R.id.fragment_recycler_view_launcher);
        final int offset = getResources().getDimensionPixelOffset(R.dimen.offset);
        mRecyclerView.addItemDecoration(new OffsetItemDecoration(offset));

        // Create LinearLayoutManager
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        // Generate Data
        final List<Application> data = new ArrayList<>();
        // Create ListAdapter
        final ListAdapter listAdapter = new ListAdapter(getContext());
        mRecyclerView.setAdapter(listAdapter);
        //------------------------------------------------------------------------------------------

        return view;
    }
}
