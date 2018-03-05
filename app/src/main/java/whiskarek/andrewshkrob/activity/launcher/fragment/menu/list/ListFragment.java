package whiskarek.andrewshkrob.activity.launcher.fragment.menu.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.MenuAdapter;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.MenuScreenFragment;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.MenuViewHolder;
import whiskarek.andrewshkrob.database.entity.ApplicationEntity;
import whiskarek.andrewshkrob.view.decoration.OffsetItemDecoration;
import whiskarek.andrewshkrob.viewmodel.ApplicationViewModel;

public class ListFragment extends MenuScreenFragment {

    private ApplicationViewModel mViewModel;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menu_screen, container,
                false);
        setRootLayout(view);
        // SET UP RECYCLER VIEW
        //------------------------------------------------------------------------------------------
        setRecyclerView((RecyclerView) view.findViewById(R.id.fragment_recycler_view_menu));
        final int offset = getResources().getDimensionPixelOffset(R.dimen.offset);
        getRecyclerView().addItemDecoration(new OffsetItemDecoration(offset));
        // Create LinearLayoutManager
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        getRecyclerView().setLayoutManager(linearLayoutManager);
        // Create ListAdapter
        MenuAdapter menuAdapter = new MenuAdapter(getContext(), MenuViewHolder.LIST_LAYOUT);
        getRecyclerView().setAdapter(menuAdapter);

        //------------------------------------------------------------------------------------------
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        PreferenceManager.getDefaultSharedPreferences(getContext())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(getContext())
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(getActivity()).get(ApplicationViewModel.class);

        subscribeUI(mViewModel);
    }

    private void subscribeUI(final ApplicationViewModel viewModel) {
        viewModel.getApplications().observe(this, new Observer<List<ApplicationEntity>>() {
            @Override
            public void onChanged(@Nullable List<ApplicationEntity> appInfos) {
                if (appInfos != null) {
                    ((MenuAdapter) getRecyclerView().getAdapter()).updateList(appInfos);
                }
            }
        });
    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences,
                                          final String key) {
        if (key.equals(getResources().getString(R.string.pref_key_sort_type))) {
            mViewModel.setSortType(Integer.parseInt(sharedPreferences
                    .getString(getResources().getString(R.string.pref_key_sort_type), "3")));
        }
    }
}
