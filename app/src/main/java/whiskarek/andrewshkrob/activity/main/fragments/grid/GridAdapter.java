package whiskarek.andrewshkrob.activity.main.fragments.grid;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yandex.metrica.YandexMetrica;

import java.util.List;

import whiskarek.andrewshkrob.Application;
import whiskarek.andrewshkrob.ContextMenuListener;
import whiskarek.andrewshkrob.LauncherApplication;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.database.ApplicationDatabase;

public class GridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @NonNull
    private final LauncherApplication mLauncherApplication;

    private List<Application> mApplicationList;

    GridAdapter(@NonNull final LauncherApplication launcherApplication) {
        mLauncherApplication = launcherApplication;
    }

    public void setApplicationList(final List<Application> applications) {
        if (mApplicationList == null) {
            mApplicationList = applications;
            notifyItemChanged(0, applications.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mApplicationList.size();
                }

                @Override
                public int getNewListSize() {
                    return applications.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mApplicationList.get(oldItemPosition).getPackageName()
                            .equals(applications.get(newItemPosition).getPackageName());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return mApplicationList.get(oldItemPosition).getPackageName()
                            .equals(applications.get(newItemPosition).getPackageName())
                            && mApplicationList.get(oldItemPosition).getAppName()
                            .equals(applications.get(newItemPosition).getAppName())
                            && mApplicationList.get(oldItemPosition).getLaunchAmount() ==
                            applications.get(newItemPosition).getLaunchAmount();
                }
            });
            mApplicationList = applications;
            result.dispatchUpdatesTo(this);
        }
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
                    YandexMetrica.reportEvent("Grid",
                            "Application launched. Position: " + adapterPosition);
                    mApplicationList.get(adapterPosition).addLaunch();
                    GridAdapter.this.mLauncherApplication
                            .startActivity(mApplicationList.get(adapterPosition).getLaunchIntent());
                    final LauncherApplication launcherApplication =
                            GridAdapter.this.mLauncherApplication;
                    launcherApplication.executors().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            ApplicationDatabase.getInstance(GridAdapter.this.mLauncherApplication,
                                    launcherApplication.executors()).applicationDao().updateLaunches(
                                    mApplicationList.get(adapterPosition).getPackageName(),
                                    mApplicationList.get(adapterPosition).getLaunchAmount()
                            );
                        }
                    });

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
                mLauncherApplication,
                mApplicationList.get(pos)
        );

        gridHolder.itemView.setOnCreateContextMenuListener(listener);

        final ImageView appIcon = gridHolder.getAppIcon();
        appIcon.setBackground(mApplicationList.get(pos).getAppIcon());

        final TextView appName = gridHolder.getAppName();
        appName.setText(mApplicationList.get(pos).getAppName());
    }

    @Override
    public int getItemCount() {
        return mApplicationList == null ? 0 : mApplicationList.size();
    }

}