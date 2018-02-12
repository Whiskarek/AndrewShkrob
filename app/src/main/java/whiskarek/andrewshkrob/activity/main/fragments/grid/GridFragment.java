package whiskarek.andrewshkrob.activity.main.fragments.grid;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import whiskarek.andrewshkrob.Application;
import whiskarek.andrewshkrob.LauncherApplication;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.Utils;
import whiskarek.andrewshkrob.activity.main.OffsetItemDecoration;
import whiskarek.andrewshkrob.activity.main.fragments.LauncherFragment;
import whiskarek.andrewshkrob.viewmodel.ApplicationListViewModel;

public class GridFragment extends LauncherFragment {

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_launcher, container,
                false);

        // SET UP RECYCLER VIEW
        //------------------------------------------------------------------------------------------
        mRecyclerView = view.findViewById(R.id.fragment_recycler_view_launcher);
        final int offset = getResources().getDimensionPixelOffset(R.dimen.offset);
        mRecyclerView.addItemDecoration(new OffsetItemDecoration(offset));
        // Get Model Type
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getContext());
        final boolean modelSolid = sharedPreferences
                .getBoolean(getString(R.string.pref_key_model_solid), false);
        final int spanCount = (modelSolid ?
                getResources().getInteger(R.integer.model_solid)
                : getResources().getInteger(R.integer.model_default));
        // Create GridLayoutManager
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        // Create GridAdapter
        GridAdapter gridAdapter = new GridAdapter((LauncherApplication) getActivity().getApplication());
        mRecyclerView.setAdapter(gridAdapter);
        //------------------------------------------------------------------------------------------

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ApplicationListViewModel viewModel =
                ViewModelProviders.of(this).get(ApplicationListViewModel.class);

        subscribeUI(viewModel);
    }

    private void subscribeUI(final ApplicationListViewModel viewModel) {
        viewModel.getApplications().observe(this, new Observer<List<Application>>() {
            @Override
            public void onChanged(@Nullable final List<Application> applications) {
                if (applications != null) {
                    Utils.sortApps(applications, getContext());
                    ((GridAdapter) getRecyclerView().getAdapter()).setApplicationList(applications);
                }
            }
        });
    }
}
