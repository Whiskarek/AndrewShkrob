package whiskarek.andrewshkrob.activity.main.fragments.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
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
import whiskarek.andrewshkrob.viewmodel.BackgroundViewModel;

public class ListFragment extends LauncherFragment {

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_launcher, container, false);
        mRootLayout = view;
        // SET UP RECYCLER VIEW
        //------------------------------------------------------------------------------------------
        mRecyclerView = view.findViewById(R.id.fragment_recycler_view_launcher);
        final int offset = getResources().getDimensionPixelOffset(R.dimen.offset);
        mRecyclerView.addItemDecoration(new OffsetItemDecoration(offset));

        // Create LinearLayoutManager
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        // Create ListAdapter
        ListAdapter listAdapter = new ListAdapter((LauncherApplication) getActivity().getApplication());
        mRecyclerView.setAdapter(listAdapter);

        //------------------------------------------------------------------------------------------
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final ApplicationListViewModel applicationViewModel =
                ViewModelProviders.of(this).get(ApplicationListViewModel.class);

        final BackgroundViewModel backgroundViewModel =
                ViewModelProviders.of(this).get(BackgroundViewModel.class);

        subscribeUI(applicationViewModel, backgroundViewModel);
    }

    private void subscribeUI(final ApplicationListViewModel applicationViewModel, final BackgroundViewModel backgroundViewModel) {
        applicationViewModel.getApplications().observe(this, new Observer<List<Application>>() {
            @Override
            public void onChanged(@Nullable final List<Application> applications) {
                if (applications != null) {
                    Utils.sortApps(applications, getContext());
                    ((ListAdapter) getRecyclerView().getAdapter()).setApplicationList(applications);
                }
            }
        });

        backgroundViewModel.getBackgroundImages().observe(this, new Observer<List<Drawable>>() {
            @Override
            public void onChanged(@Nullable List<Drawable> drawables) {
                if (drawables != null) {
                    if (drawables.size() != 1) {
                        setBackground(drawables.get(1));
                    } else {
                        setBackground(null);
                    }
                } else {
                    setBackground(null);
                }
            }
        });
    }
}
