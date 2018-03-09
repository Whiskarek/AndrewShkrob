package whiskarek.andrewshkrob.activity.launcher.fragment.menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import whiskarek.andrewshkrob.LauncherApplication;
import whiskarek.andrewshkrob.LauncherExecutors;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.database.dao.ApplicationDao;
import whiskarek.andrewshkrob.database.entity.ApplicationEntity;
import whiskarek.andrewshkrob.dragndrop.ItemTouchHelperAdapter;
import whiskarek.andrewshkrob.dragndrop.OnStartDragListener;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder>
        implements ItemTouchHelperAdapter {

    private final Context mContext;
    private final int mLayoutType;
    private final OnStartDragListener mOnStartDragListener;
    private List<ApplicationEntity> mAppInfoList;

    public MenuAdapter(final Context context, final int layoutType,
                       final OnStartDragListener onStartDragListener)
    {
        mContext = context;
        mLayoutType = layoutType;
        mAppInfoList = new ArrayList<>();
        mOnStartDragListener = onStartDragListener;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final View view;
        if (mLayoutType == MenuViewHolder.GRID_LAYOUT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
        }
        final MenuViewHolder viewHolder = new MenuViewHolder(view, mLayoutType);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    try {
                        mContext.startActivity(mAppInfoList.get(adapterPosition).getIntent());

                        LauncherExecutors.getInstance().databaseIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                final ApplicationDao appDao =
                                        ((LauncherApplication) mContext.getApplicationContext())
                                                .getDatabase()
                                                .applicationDao();
                                appDao.setLaunchAmount(
                                        mAppInfoList.get(adapterPosition).getPackageName(),
                                        mAppInfoList.get(adapterPosition).getLaunchAmount() + 1
                                );
                            }
                        });
                    } catch (Exception e) {

                    }
                }
            }
        });

        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                final int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        mOnStartDragListener.onStartDrag(viewHolder);
                        return true;
                    }
                }
                return false;
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, final int position) {
        bindView(holder);
    }

    private void bindView(@NonNull final MenuViewHolder menuHolder) {
        final int pos = menuHolder.getAdapterPosition();

        final ContextMenuListener listener = new ContextMenuListener(
                mContext,
                mAppInfoList.get(pos)
        );

        menuHolder.itemView.setOnCreateContextMenuListener(listener);

        final ImageView appIcon = menuHolder.getAppIcon();
        appIcon.setBackground(mAppInfoList.get(pos).getIcon());

        final TextView appName = menuHolder.getAppName();
        appName.setText(mAppInfoList.get(pos).getLabel());

        if (mLayoutType == MenuViewHolder.LIST_LAYOUT) {
            final TextView appPackageName = menuHolder.getAppPackageName();
            appPackageName.setText(mAppInfoList.get(pos).getPackageName());
        }

    }

    @Override
    public int getItemCount() {
        return mAppInfoList == null ? 0 : mAppInfoList.size();
    }

    @Override
    public boolean onItemMove(final int fromPosition, final int toPosition) {
        if (fromPosition < mAppInfoList.size() && toPosition < mAppInfoList.size()) {
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mAppInfoList, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mAppInfoList, i, i - 1);
                }
            }
            notifyItemMoved(fromPosition, toPosition);
        }
        return true;
    }

    @Override
    public void onItemDismiss(final int position) {
        mAppInfoList.remove(position);
        notifyItemRemoved(position);
    }

    public void updateList(final List<ApplicationEntity> appInfoList) {
        if (mAppInfoList == null || mAppInfoList.size() == 0) {
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
                    return mAppInfoList.get(oldItemPosition).getIntent()
                            .equals(appInfoList.get(newItemPosition).getIntent());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return mAppInfoList.get(oldItemPosition).getPackageName()
                            .equals(appInfoList.get(newItemPosition).getPackageName())
                            && mAppInfoList.get(oldItemPosition).getLabel()
                            .equals(appInfoList.get(newItemPosition).getLabel());
                }
            });
            mAppInfoList = appInfoList;
            result.dispatchUpdatesTo(this);
        }
    }
}
