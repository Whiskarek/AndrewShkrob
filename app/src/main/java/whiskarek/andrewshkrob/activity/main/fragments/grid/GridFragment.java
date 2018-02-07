package whiskarek.andrewshkrob.activity.main.fragments.grid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
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
import whiskarek.andrewshkrob.activity.main.Application;
import whiskarek.andrewshkrob.activity.main.OffsetItemDecoration;
import whiskarek.andrewshkrob.activity.main.fragments.LauncherFragment;

public class GridFragment extends LauncherFragment {

    private RecyclerView mRecyclerView;
    private boolean mViewCreated = false;

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
        final GridAdapter gridAdapter = new GridAdapter(getContext(), data);
        mRecyclerView.setAdapter(gridAdapter);
        //------------------------------------------------------------------------------------------

        mViewCreated = true;
        return view;
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        final int position = ((GridAdapter) mRecyclerView.getAdapter()).getPosition();

        switch (item.getItemId()) {
            case R.id.context_menu_app_delete: {
                Log.i(getContext().getResources().getString(R.string.log_tag_launcher),
                        "Delete app, position: " + position);
                final Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.setData(Uri.parse("package:" + ((GridAdapter)
                        mRecyclerView.getAdapter()).getApplication(position).getPackageName()));
                startActivity(intent);
                break;
            }
            case R.id.context_menu_app_frequency: {
                Log.i(getContext().getResources().getString(R.string.log_tag_launcher),
                        "Frequency app, position: " + position);

                Toast.makeText(getContext(), getString(R.string.context_menu_app_launch_amount) + ": " + ((GridAdapter)
                        mRecyclerView.getAdapter()).getApplication(position).getLaunchAmount(), Toast.LENGTH_SHORT).show();

                break;
            }
            case R.id.context_menu_app_info: {
                Log.i(getContext().getResources().getString(R.string.log_tag_launcher),
                        "Info app, position: " + position);

                //final Intent intent = new Intent(Intent.ACTION_)
                break;
            }
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void setData(List<Application> data) {
        if (mViewCreated) {
            ((GridAdapter) mRecyclerView.getAdapter()).setData(data);
        }
    }
}
