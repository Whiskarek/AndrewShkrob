package whiskarek.andrewshkrob.activity.launcher.fragment.menu.grid;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.MenuAdapter;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.MenuScreenFragment;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.MenuViewHolder;
import whiskarek.andrewshkrob.database.entity.ApplicationEntity;
import whiskarek.andrewshkrob.dragndrop.EditItemTouchHelperCallback;
import whiskarek.andrewshkrob.dragndrop.OnStartDragListener;
import whiskarek.andrewshkrob.view.decoration.OffsetItemDecoration;
import whiskarek.andrewshkrob.viewmodel.ApplicationViewModel;

public class GridFragment extends MenuScreenFragment implements OnStartDragListener{

    private ApplicationViewModel mViewModel;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menu_screen, container,
                false);
        setRootLayout(view);
        view.setBackgroundColor(Color.WHITE);
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

        final MenuAdapter menuAdapter = new MenuAdapter(getContext(), MenuViewHolder.GRID_LAYOUT, this);

        final ItemTouchHelper.Callback callback = new EditItemTouchHelperCallback(menuAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(getRecyclerView());

        getRecyclerView().setAdapter(menuAdapter);
        //------------------------------------------------------------------------------------------
        return view;
    }

    @Override
    public void onStartDrag(final RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mViewModel = ViewModelProviders.of(getActivity()).get(ApplicationViewModel.class);
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        subscribeUI(mViewModel);
    }

    private void subscribeUI(final ApplicationViewModel viewModel) {
        viewModel.getApplications().observe(this, new Observer<List<ApplicationEntity>>() {
            @Override
            public void onChanged(final @Nullable List<ApplicationEntity> appInfos) {
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

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences,
                                          final String key) {
        if (key.equals(getResources().getString(R.string.pref_key_sort_type))) {
            mViewModel.setSortType(Integer.parseInt(sharedPreferences
                    .getString(getResources().getString(R.string.pref_key_sort_type), "3")));
        } else if (key.equals(getResources().getString(R.string.pref_key_model_solid))) {
            final boolean modelSolid = sharedPreferences
                    .getBoolean(getResources().getString(R.string.pref_key_model_solid), false);

            mViewModel.setSolidModel(modelSolid);
        }
    }
}
