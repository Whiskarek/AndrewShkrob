package whiskarek.andrewshkrob.activity.main.fragments.launcher;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.R;

public class LauncherFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Application>> {

    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_launcher, container,
                false);

        // SET UP RECYCLER VIEW
        //------------------------------------------------------------------------------------------
        mRecyclerView =
                (RecyclerView) view.findViewById(R.id.fragment_recycler_view_launcher);
        final int offset = getResources().getDimensionPixelOffset(R.dimen.offset);
        mRecyclerView.addItemDecoration(new OffsetItemDecoration(offset));
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
        mRecyclerView.setLayoutManager(gridLayoutManager);
        // Generate Data
        final List<Application> data = new ArrayList<>();
        // Create GridAdapter
        final LauncherAdapter gridAdapter = new LauncherAdapter(getContext(), data);
        mRecyclerView.setAdapter(gridAdapter);
        //------------------------------------------------------------------------------------------

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //((AppCompatActivity)getActivity()).getSupportActionBar().
        getLoaderManager().initLoader(1, null, this);
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        final int position = ((LauncherAdapter) mRecyclerView.getAdapter()).getPosition();

        switch (item.getItemId()) {
            case R.id.context_menu_app_delete: {
                Log.i(getContext().getResources().getString(R.string.log_tag_launcher),
                        "Delete app, position: " + position);
                final Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.setData(Uri.parse("package:" + ((LauncherAdapter)
                        mRecyclerView.getAdapter()).getApplication(position).getPackageName()));
                startActivity(intent);
            }
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public Loader<List<Application>> onCreateLoader(int id, Bundle args) {
        return new ApplicationsLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<Application>> loader, List<Application> data) {
        Log.i("BROADCAST", "" + data.size());
        ((LauncherAdapter) mRecyclerView.getAdapter()).setData(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Application>> loader) {
        ((LauncherAdapter) mRecyclerView.getAdapter()).setData(null);
    }
}
