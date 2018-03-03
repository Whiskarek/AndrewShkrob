package whiskarek.andrewshkrob.activity.launcher.fragment.menu.grid;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.MenuAdapter;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.MenuScreenFragment;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.MenuViewHolder;
import whiskarek.andrewshkrob.database.entity.ApplicationInfoEntity;
import whiskarek.andrewshkrob.view.decoration.OffsetItemDecoration;
import whiskarek.andrewshkrob.viewmodel.AppInfoViewModel;

public class GridFragment extends MenuScreenFragment {

    private AppInfoViewModel mViewModel;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_launcher, container,
                false);
        setRootLayout(view);
        // SET UP RECYCLER VIEW
        //------------------------------------------------------------------------------------------
        setRecyclerView((RecyclerView) view.findViewById(R.id.fragment_recycler_view_menu));
        final int offset = getResources().getDimensionPixelOffset(R.dimen.offset);
        getRecyclerView().addItemDecoration(new OffsetItemDecoration(offset));
        // Get Model Type
        boolean modelSolid;
        if (mViewModel.getModelType().getValue() != null)
        {
            modelSolid = mViewModel.getModelType().getValue();
        } else {
            final SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(getContext());
            modelSolid = sharedPreferences
                    .getBoolean(getString(R.string.pref_key_model_solid), false);
        }
        final int spanCount = (modelSolid ?
                getResources().getInteger(R.integer.model_solid)
                : getResources().getInteger(R.integer.model_default));
        // Create GridLayoutManager
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        getRecyclerView().setLayoutManager(gridLayoutManager);
        // Create GridAdapter
        final MenuAdapter menuAdapter = new MenuAdapter(getContext(), MenuViewHolder.GRID_LAYOUT);
        getRecyclerView().setAdapter(menuAdapter);
        //------------------------------------------------------------------------------------------
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mViewModel = ViewModelProviders.of(getActivity()).get(AppInfoViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        subscribeUI(mViewModel);
    }

    private void subscribeUI(final AppInfoViewModel viewModel) {
        viewModel.getApplications().observe(this, new Observer<List<ApplicationInfoEntity>>() {
            @Override
            public void onChanged(final @Nullable List<ApplicationInfoEntity> appInfos) {
                if (appInfos != null) {
                    ((MenuAdapter) getRecyclerView().getAdapter()).updateList(appInfos);
                }
            }
        });

        viewModel.getModelType().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean modelSolid) {
                ((GridLayoutManager) getRecyclerView().getLayoutManager()).setSpanCount((modelSolid ?
                        getResources().getInteger(R.integer.model_solid)
                        : getResources().getInteger(R.integer.model_default)));
            }
        });
    }
}
