package whiskarek.andrewshkrob.activity.main.fragments.grid;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import whiskarek.andrewshkrob.R;

public class Holder {

    static class GridHolder extends RecyclerView.ViewHolder {
        private final ImageView mAppIcon;
        private final TextView mAppName;

        GridHolder(final View view) {
            super(view);
            mAppIcon = (ImageView) view.findViewById(R.id.launcher_app_icon);
            mAppName = (TextView) view.findViewById(R.id.launcher_app_name);
        }

        ImageView getAppIcon() {
            return mAppIcon;
        }

        TextView getAppName() {
            return mAppName;
        }
    }

}
