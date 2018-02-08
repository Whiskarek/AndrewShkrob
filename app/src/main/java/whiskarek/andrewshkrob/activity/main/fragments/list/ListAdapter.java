package whiskarek.andrewshkrob.activity.main.fragments.list;

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

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final Context mContext;

    ListAdapter(@NonNull final Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        final Holder.ListHolder viewHolder = new Holder.ListHolder(view);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Log.i(mContext.getResources().getString(R.string.log_tag_button_click),
                            "Click in Launcher Recycler View in position " + adapterPosition);
                    MainActivity.getApps().get(adapterPosition).addLaunch();
                    Utils.addLaunch(MainActivity.getDatabase(), MainActivity.getApps().get(adapterPosition));
                    mContext.startActivity(MainActivity.getApps().get(adapterPosition)
                            .getLaunchIntent());
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindListView((Holder.ListHolder) holder);
    }

    private void bindListView(@NonNull final Holder.ListHolder listHolder) {
        final int pos = listHolder.getAdapterPosition();

        final ContextMenuListener listener = new ContextMenuListener(
                mContext,
                MainActivity.getApps().get(pos)
        );

        listHolder.itemView.setOnCreateContextMenuListener(listener);

        final ImageView appIcon = listHolder.getAppIcon();
        appIcon.setBackground(MainActivity.getApps().get(pos).getAppIcon());

        final TextView appName = listHolder.getAppName();
        appName.setText(MainActivity.getApps().get(pos).getAppName());

        final TextView appPackageName = listHolder.getAppPackageName();
        appPackageName.setText(MainActivity.getApps().get(pos).getPackageName());
    }

    @Override
    public int getItemCount() {
        final List<Application> apps = MainActivity.getApps();
        return apps.size();
    }

}
