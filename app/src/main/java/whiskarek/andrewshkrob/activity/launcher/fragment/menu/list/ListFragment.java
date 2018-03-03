package whiskarek.andrewshkrob.activity.launcher.fragment.menu.list;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
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
import whiskarek.andrewshkrob.database.entity.ApplicationInfoEntity;
import whiskarek.andrewshkrob.view.decoration.OffsetItemDecoration;
import whiskarek.andrewshkrob.viewmodel.AppInfoViewModel;

public class ListFragment extends MenuScreenFragment {

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
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final AppInfoViewModel viewModel =
                ViewModelProviders.of(getActivity()).get(AppInfoViewModel.class);

        subscribeUI(viewModel);
    }

    private void subscribeUI(final AppInfoViewModel viewModel) {
        viewModel.getApplications().observe(this, new Observer<List<ApplicationInfoEntity>>() {
            @Override
            public void onChanged(@Nullable List<ApplicationInfoEntity> appInfos) {
                if (appInfos != null) {
                    ((MenuAdapter) getRecyclerView().getAdapter()).updateList(appInfos);
                }
            }
        });
    }

}
