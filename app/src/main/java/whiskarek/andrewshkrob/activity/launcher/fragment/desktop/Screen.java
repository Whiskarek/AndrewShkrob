package whiskarek.andrewshkrob.activity.launcher.fragment.desktop;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Random;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.viewmodel.ApplicationViewModel;

public class Screen extends Fragment {

    private int mPage;
    private ApplicationViewModel mViewModel;

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

        mPage = getArguments().getInt("page", 1);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater,
                             @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        final View view =
                inflater.inflate(R.layout.fragment_desktop_screen, container, false);

        final RecyclerView recyclerView = view.findViewById(R.id.desktop_screen_item_container);

        final Random rand = new Random();
        view.setBackgroundColor(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(ApplicationViewModel.class);
    }
}
