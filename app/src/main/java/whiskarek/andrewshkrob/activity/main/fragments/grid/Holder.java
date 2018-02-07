package whiskarek.andrewshkrob.activity.main.fragments.grid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.main.Util;

public class Holder {

    static class GridHolder extends RecyclerView.ViewHolder {
        private final ImageView mAppIcon;
        private final TextView mAppName;

        private final View.OnCreateContextMenuListener mContextMenuListener = new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(Menu.NONE, R.id.context_menu_app_delete, Menu.NONE, R.string.context_menu_app_delete);
                menu.add(Menu.NONE, R.id.context_menu_app_frequency, Menu.NONE, R.string.context_menu_app_frequency);
                menu.add(Menu.NONE, R.id.context_menu_app_info, Menu.NONE, R.string.context_menu_app_info);
            }
        };

        GridHolder(final View view) {
            super(view);
            mAppIcon = (ImageView) view.findViewById(R.id.launcher_app_icon);
            mAppName = (TextView) view.findViewById(R.id.launcher_app_name);

            view.setOnCreateContextMenuListener(mContextMenuListener);
        }

        ImageView getAppIcon() {
            return mAppIcon;
        }

        TextView getAppName() {
            return mAppName;
        }
    }

}
