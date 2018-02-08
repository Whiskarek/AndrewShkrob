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

import whiskarek.andrewshkrob.ContextMenuListener;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.Application;
import whiskarek.andrewshkrob.Utils;
import whiskarek.andrewshkrob.activity.main.MainActivity;

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final Context mContext;

    GridAdapter(@NonNull final Context context) {
        mContext = context;
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
                    MainActivity.getApps().get(adapterPosition).addLaunch();
                    Utils.addLaunch(MainActivity.getDatabase(), MainActivity.getApps().get(adapterPosition));
                    mContext.startActivity(MainActivity.getApps().get(adapterPosition).getLaunchIntent());
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindGridView((Holder.GridHolder) holder);
    }

    private void bindGridView(@NonNull final Holder.GridHolder gridHolder) {
        final int pos = gridHolder.getAdapterPosition();

        final ContextMenuListener listener = new ContextMenuListener(
                mContext,
                MainActivity.getApps().get(pos)
        );

        gridHolder.itemView.setOnCreateContextMenuListener(listener);

        final ImageView appIcon = gridHolder.getAppIcon();
        appIcon.setBackground(MainActivity.getApps().get(pos).getAppIcon());

        final TextView appName = gridHolder.getAppName();
        appName.setText(MainActivity.getApps().get(pos).getAppName());
    }

    @Override
    public int getItemCount() {
        final List<Application> apps = MainActivity.getApps();
        return apps.size();
    }

}