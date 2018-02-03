package whiskarek.andrewshkrob.activity.main.fragments.list;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import whiskarek.andrewshkrob.R;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final Context mContext;
    @NonNull private final List<Integer> mData;

    public ListAdapter(@NonNull final Context context, @NonNull final List<Integer> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        final Holder.ListHolder viewHolder = new Holder.ListHolder(view);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Log.i(mContext.getResources().getString(R.string.log_tag_button_click),
                            "Long click in List Recycler View in position " + adapterPosition);
                    Snackbar.make(v, R.string.dialog_delete_element, Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.dialog_delete_element_yes, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.i(mContext.getResources()
                                                    .getString(R.string.log_tag_button_click),
                                            "Snackbar button in "
                                                    + "ListFragment in position "
                                                    + adapterPosition + " was clicked");
                                    mData.remove(adapterPosition);
                                    notifyItemRemoved(adapterPosition);
                                    Log.i(mContext.getResources()
                                                    .getString(R.string.log_tag_button_click),
                                            "Item in ListFragment in position "
                                                    + adapterPosition + "was deleted");
                                }
                            })
                            .setDuration(5000)
                            .show();
                }

                return true;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindGridView((Holder.ListHolder) holder, position);
    }

    private void bindGridView(@NonNull final Holder.ListHolder listHolder, final int position) {
        final View itemColorView = listHolder.getItemColorView();
        itemColorView.setBackgroundColor(mData.get(position));

        final TextView itemColorName = listHolder.getItemColorName();
        final int color = ((ColorDrawable) itemColorView.getBackground()).getColor();
        itemColorName.setText("#" + Integer.toHexString(color).toUpperCase().replaceFirst("FF", ""));

        final TextView itemRandomText = listHolder.getItemRandomText();
        final String text = mContext.getResources().getStringArray(R.array.fragment_list_random_text)[new Random().nextInt(9)];
        itemRandomText.setText(text);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addItem(final int color) {
        mData.add(0, color);
        notifyItemInserted(0);
    }
}
