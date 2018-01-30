package whiskarek.andrewshkrob.activity.list;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import whiskarek.andrewshkrob.R;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final List<Integer> mData;
    private final Context mContext;

    public ListAdapter(@NonNull final List<Integer> data, Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_linear_item, parent, false);
        return new Holder.LinearHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindGridView((Holder.LinearHolder) holder, position);
    }

    private void bindGridView(@NonNull final Holder.LinearHolder gridHolder, final int position) {
        final View launcherImage = gridHolder.getListImage();
        launcherImage.setBackgroundColor(mData.get(position));
        launcherImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                Snackbar.make(v, "position = " + position, Snackbar.LENGTH_SHORT).show();
                return true;
            }
        });

        final TextView listMainText = gridHolder.getListMainText();
        final int color = ((ColorDrawable) launcherImage.getBackground()).getColor();
        listMainText.setText("#" + Integer.toHexString(color).toUpperCase().replaceFirst("FF", ""));

        final TextView listSecondText = gridHolder.getListSecondText();
        final String text = mContext.getResources().getStringArray(R.array.array_list_second_text)[new Random().nextInt(9)];
        listSecondText.setText(text);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
