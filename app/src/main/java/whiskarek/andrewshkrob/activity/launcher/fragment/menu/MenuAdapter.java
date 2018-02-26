package whiskarek.andrewshkrob.activity.launcher.fragment.menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.AppInfo;
import whiskarek.andrewshkrob.InstalledApplicationsParser;
import whiskarek.andrewshkrob.R;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context mContext;
    private final int mLayoutType;
    private List<AppInfo> mAppInfoList;

    public MenuAdapter(final Context context, final int layoutType)
    {
        mContext = context;
        mLayoutType = layoutType;
        mAppInfoList = InstalledApplicationsParser.appInfoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        View view;
        if (mLayoutType == MenuViewHolder.GRID_LAYOUT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
        }
        final MenuViewHolder viewHolder = new MenuViewHolder(view, mLayoutType);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        bindView((MenuViewHolder) holder);
    }

    private void bindView(@NonNull final MenuViewHolder menuHolder) {
        final int pos = menuHolder.getAdapterPosition();

        /*final ContextMenuListener listener = new ContextMenuListener(
                mLauncherApplication,
                mApplicationList.get(pos)
        );*/

        //menuHolder.itemView.setOnCreateContextMenuListener(listener);

        final ImageView appIcon = menuHolder.getAppIcon();
        appIcon.setBackground(mAppInfoList.get(pos).getApplicationIcon());

        final TextView appName = menuHolder.getAppName();
        appName.setText(mAppInfoList.get(pos).getAppName());

        if (mLayoutType == MenuViewHolder.LIST_LAYOUT) {
            final TextView appPackageName = menuHolder.getAppPackageName();
            appPackageName.setText(mAppInfoList.get(pos).getPackageName());
        }

    }

    @Override
    public int getItemCount() {
        return mAppInfoList == null ? 0 : mAppInfoList.size();
    }

    public void updateList(final List<AppInfo> appInfoList) {
        if (mAppInfoList == null) {
            mAppInfoList = appInfoList;
            notifyDataSetChanged();
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mAppInfoList.size();
                }

                @Override
                public int getNewListSize() {
                    return appInfoList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mAppInfoList.get(oldItemPosition).getPackageName()
                            .equals(appInfoList.get(newItemPosition).getPackageName());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return mAppInfoList.get(oldItemPosition).getPackageName()
                            .equals(appInfoList.get(newItemPosition).getPackageName())
                            && mAppInfoList.get(oldItemPosition).getAppName()
                            .equals(appInfoList.get(newItemPosition).getAppName())
                            && mAppInfoList.get(oldItemPosition).getLaunchAmount() ==
                            appInfoList.get(newItemPosition).getLaunchAmount();
                }
            });
            mAppInfoList = appInfoList;
            result.dispatchUpdatesTo(this);
        }
    }
}
