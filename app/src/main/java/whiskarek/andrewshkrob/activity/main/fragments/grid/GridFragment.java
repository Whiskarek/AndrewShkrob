package whiskarek.andrewshkrob.activity.main.fragments.grid;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import whiskarek.andrewshkrob.R;

public class GridFragment extends Fragment {

    private static final int mDataSize = 1000;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_grid, container, false );

        // SET UP RECYCLER VIEW
        //------------------------------------------------------------------------------------------
        final RecyclerView recyclerView =
                (RecyclerView) view.findViewById(R.id.fragment_recycler_view_grid);
        final int offset = getResources().getDimensionPixelOffset(R.dimen.offset);
        recyclerView.addItemDecoration(new OffsetItemDecoration(offset));
        // Get Model Type
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getContext());
        final boolean modelSolid = sharedPreferences
                .getBoolean(getString(R.string.pref_key_model_solid), false);
        final int spanCount = (modelSolid == true ?
                getResources().getInteger(R.integer.model_solid)
                : getResources().getInteger(R.integer.model_default));
        // Create GridLayoutManager
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);
        // Generate Data
        final List<Integer> data = generateData();
        // Create GridAdapter
        final GridAdapter gridAdapter = new GridAdapter(getContext(), data);
        recyclerView.setAdapter(gridAdapter);
        //------------------------------------------------------------------------------------------

        final FloatingActionButton fab =
                (FloatingActionButton) view.findViewById(R.id.fragment_grid_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(getContext().getResources().getString(R.string.log_tag_button_click),
                        "Floating Action Button in GridFragment was clicked");
                final Random rand = new Random();
                gridAdapter.addItem(Color.rgb(rand.nextInt(256),
                        rand.nextInt(256), rand.nextInt(256)));
                recyclerView.scrollToPosition(0);
                Log.i(getContext().getResources().getString(R.string.log_tag_button_click),
                        "New item was inserted into RecyclerView in GridFragment");
            }
        });

        return view;
    }

    /**
     *  This method is used to generate data for recycler view.
     */
    private List<Integer> generateData() {
        final List<Integer> colors = new ArrayList<>();
        final Random rand = new Random();
        for (int i = 0; i < mDataSize; i++) {
            final int color = Color.rgb(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
            colors.add(color);
        }
        return colors;
    }

}
