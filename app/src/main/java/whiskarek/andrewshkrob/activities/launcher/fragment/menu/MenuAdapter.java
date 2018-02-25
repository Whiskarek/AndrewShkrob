package whiskarek.andrewshkrob.activities.launcher.fragment.menu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import whiskarek.andrewshkrob.R;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final Context mContext;
    private final int mLayoutType;

    public MenuAdapter(final Context context, final int layoutType)
    {
        mContext = context;
        mLayoutType = layoutType;
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
        //appIcon.setBackground(mApplicationList.get(pos).getAppIcon());

        final TextView appName = menuHolder.getAppName();
        //appName.setText(mApplicationList.get(pos).getAppName());

        if (mLayoutType == MenuViewHolder.LIST_LAYOUT) {
            final TextView appPackageName = menuHolder.getAppPackageName();
            //appPackageName.setText(mApplicationList.get(pos).getPackageName());
        }

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
