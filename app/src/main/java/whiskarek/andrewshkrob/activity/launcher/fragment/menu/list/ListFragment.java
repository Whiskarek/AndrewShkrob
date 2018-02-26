package whiskarek.andrewshkrob.activity.launcher.fragment.menu.list;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.MenuAdapter;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.MenuScreenFragment;
import whiskarek.andrewshkrob.activity.launcher.fragment.menu.MenuViewHolder;
import whiskarek.andrewshkrob.view.decoration.OffsetItemDecoration;

public class ListFragment extends MenuScreenFragment {

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_launcher, container,
                false);
        setRootLayout(view);
        mRootLayout.setBackgroundColor(Color.RED);
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

}
