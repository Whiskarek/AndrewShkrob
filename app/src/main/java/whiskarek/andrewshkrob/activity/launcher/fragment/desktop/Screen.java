package whiskarek.andrewshkrob.activity.launcher.fragment.desktop;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.database.entity.ShortcutEntity;
import whiskarek.andrewshkrob.viewmodel.ApplicationViewModel;
import whiskarek.andrewshkrob.viewmodel.ShortcutViewModel;

public class Screen extends Fragment {

    private int mScreenNum;
    private RecyclerView mRecyclerView;
    private ApplicationViewModel mAppViewModel;
    private ShortcutViewModel mShortcutViewModel;

    public static Screen getScreen(final int page) {
        final Screen screen = new Screen();

        Bundle args = new Bundle();
        args.putInt("page", page);
        screen.setArguments(args);

        return screen;
    }

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mScreenNum = getArguments().getInt("page", 1);
        mAppViewModel = ViewModelProviders.of(getActivity()).get(ApplicationViewModel.class);
        mShortcutViewModel = ViewModelProviders.of(getActivity()).get(ShortcutViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view =
                inflater.inflate(R.layout.fragment_desktop_screen, container, false);

        mRecyclerView = view.findViewById(R.id.desktop_screen_item_container);

        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setAdapter(new ScreenAdapter(getContext()));
        mRecyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mShortcutViewModel.getShortcutList().observe(this, new Observer<List<ShortcutEntity>>() {
            @Override
            public void onChanged(@Nullable final List<ShortcutEntity> shortcutEntities) {
                if (shortcutEntities == null) {
                    return;
                }

                ((ScreenAdapter) mRecyclerView.getAdapter()).setData(mAppViewModel.getShortcuts(shortcutEntities));
            }
        });
    }
}
