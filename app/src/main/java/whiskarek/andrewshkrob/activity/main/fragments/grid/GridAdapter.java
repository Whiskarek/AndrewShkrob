package whiskarek.andrewshkrob.activity.main.fragments.grid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.main.Application;

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final Context mContext;
    @NonNull
    private final List<Application> mData;
    private int mPosition = -1;

    public GridAdapter(@NonNull final Context context, @NonNull final List<Application> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grid_item, parent, false);
        final Holder.GridHolder viewHolder = new Holder.GridHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Log.i(mContext.getResources().getString(R.string.log_tag_button_click),
                            "Click in Launcher Recycler View in position " + adapterPosition);
                    mData.get(adapterPosition).addLaunch();
                    mContext.startActivity(mData.get(adapterPosition).getLaunchIntent());
                }
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Log.i(mContext.getResources().getString(R.string.log_tag_button_click),
                            "Long click in Launcher Recycler View in position "
                                    + adapterPosition);
                    setPosition(adapterPosition);
                }
                return false;
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindGridView((Holder.GridHolder) holder, position);
    }

    private void bindGridView(@NonNull final Holder.GridHolder gridHolder, final int position) {
        final ImageView appIcon = gridHolder.getAppIcon();
        appIcon.setBackground(mData.get(position).getAppIcon());

        final TextView appName = gridHolder.getAppName();
        appName.setText(mData.get(position).getAppName());
    }

    @Override
    public int getItemCount() {
        return (mData == null ? 0 : mData.size());
    }

    public void setData(final List<Application> apps) {
        clearData();
        if (apps != null) {
            mData.addAll(apps);
            notifyItemRangeChanged(0, mData.size());
        }
    }

    private void clearData() {
        final int size = mData.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mData.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }

    public Application getApplication(final int position) {
        return mData.get(position);
    }

    public void setPosition(final int position) {
        mPosition = position;
    }

    public int getPosition() {
        return mPosition;
    }
}