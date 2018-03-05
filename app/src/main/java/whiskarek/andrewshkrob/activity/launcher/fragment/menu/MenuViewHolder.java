package whiskarek.andrewshkrob.activity.launcher.fragment.menu;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.model.MenuViewHolderModel;

public class MenuViewHolder extends RecyclerView.ViewHolder implements MenuViewHolderModel{

    public final static int GRID_LAYOUT = 1;
    public final static int LIST_LAYOUT = 2;

    private final ImageView mAppIcon;
    private final TextView mAppName;
    private final TextView mAppPackageName;

    public MenuViewHolder(final View view, final int layoutType) {
        super(view);
        mAppIcon = view.findViewById(R.id.launcher_app_icon);
        mAppName = view.findViewById(R.id.launcher_app_name);
        if (layoutType == LIST_LAYOUT) {
            mAppPackageName = view.findViewById(R.id.launcher_app_package_name);
        } else {
            mAppPackageName = null;
        }

    }

    @Override
    public ImageView getAppIcon() {
        return mAppIcon;
    }

    @Override
    public TextView getAppName() {
        return mAppName;
    }

    @Override
    public TextView getAppPackageName() {
        return mAppPackageName;
    }

}
