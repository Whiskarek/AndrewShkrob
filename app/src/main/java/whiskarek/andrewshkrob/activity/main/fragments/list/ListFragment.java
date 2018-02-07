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
import whiskarek.andrewshkrob.activity.main.Application;
import whiskarek.andrewshkrob.activity.main.fragments.LauncherFragment;
import whiskarek.andrewshkrob.activity.main.OffsetItemDecoration;
import whiskarek.andrewshkrob.activity.main.fragments.grid.GridAdapter;

public class ListFragment extends LauncherFragment {

    private RecyclerView mRecyclerView = null;
    private boolean mViewCreated = false;

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
        final ListAdapter listAdapter = new ListAdapter(getContext(), data);
        mRecyclerView.setAdapter(listAdapter);
        //------------------------------------------------------------------------------------------

        mViewCreated = true;
        return view;
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        final int position = ((ListAdapter) mRecyclerView.getAdapter()).getPosition();

        switch (item.getItemId()) {
            case R.id.context_menu_app_delete: {
                Log.i(getContext().getResources().getString(R.string.log_tag_launcher),
                        "Delete app, position: " + position);
                final Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.setData(Uri.parse("package:" + ((ListAdapter)
                        mRecyclerView.getAdapter()).getApplication(position).getPackageName()));
                startActivity(intent);
                break;
            }
            case R.id.context_menu_app_frequency: {
                Log.i(getContext().getResources().getString(R.string.log_tag_launcher),
                        "Frequency app, position: " + position);

                Toast.makeText(getContext(), getString(R.string.context_menu_app_launch_amount) + ": " + ((ListAdapter)
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
            ((ListAdapter) mRecyclerView.getAdapter()).setData(data);
        }
    }
}
