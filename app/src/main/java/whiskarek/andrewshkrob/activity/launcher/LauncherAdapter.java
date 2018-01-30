package whiskarek.andrewshkrob.activity.launcher;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import whiskarek.andrewshkrob.R;

public class LauncherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final List<Integer> mData;

    public LauncherAdapter(@NonNull final List<Integer> data) {
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.launcher_grid_item, parent, false);
        return new Holder.GridHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindGridView((Holder.GridHolder) holder, position);
    }

    private void bindGridView(@NonNull final Holder.GridHolder gridHolder, final int position) {
        final View launcherImage = gridHolder.getLauncherImage();
        launcherImage.setBackgroundColor(mData.get(position));
        launcherImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                Snackbar.make(v, "position = " + position, Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });

        final TextView launcherText = gridHolder.getLauncherText();
        final int color = ((ColorDrawable) launcherImage.getBackground()).getColor();
        launcherText.setText("#" + Integer.toHexString(color).toUpperCase().replaceFirst("FF", ""));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
