package whiskarek.andrewshkrob.activity.launcher.fragment.menu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import whiskarek.andrewshkrob.AppInfo;
import whiskarek.andrewshkrob.R;

public class ContextMenuListener implements
        View.OnCreateContextMenuListener,
        MenuItem.OnMenuItemClickListener{

    private final AppInfo mAppInfo;
    private final Context mContext;

    public ContextMenuListener(final Context context, final AppInfo appInfo) {
        mContext = context;
        mAppInfo = appInfo;
    }

    @Override
    public void onCreateContextMenu(final ContextMenu menu,
                                    final View v, final ContextMenu.ContextMenuInfo menuInfo) {

        boolean systemApp = mAppInfo.isSystemApp();
        menu.setHeaderTitle(mAppInfo.getAppName());
        if (!systemApp) {
            final MenuItem delete = menu.add(Menu.NONE, R.id.context_menu_app_delete, Menu.NONE,
                    R.string.context_menu_app_delete);
            delete.setOnMenuItemClickListener(this);
        }
        final String launchAmountText =
                mContext.getString(R.string.context_menu_app_launch_amount) +
                        ": " +
                        mAppInfo.getLaunchAmount();
        final MenuItem launchAmount =
                menu.add(Menu.NONE, R.id.context_menu_app_frequency, Menu.NONE, launchAmountText);

        final MenuItem info = menu.add(Menu.NONE, R.id.context_menu_app_info, Menu.NONE,
                R.string.context_menu_app_info);
        info.setOnMenuItemClickListener(this);

        final MenuItem addToDesktop = menu.add(Menu.NONE, R.id.context_menu_app_add_to_desktop,
                Menu.NONE, R.string.context_menu_app_add_to_desktop);
        addToDesktop.setOnMenuItemClickListener(this);

    }

    @Override
    public boolean onMenuItemClick(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu_app_delete: {
                Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setData(Uri.parse("package:" + mAppInfo.getPackageName()));
                mContext.startActivity(intent);
                break;
            }
            case R.id.context_menu_app_info: {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + mAppInfo.getPackageName()));
                mContext.startActivity(intent);
                break;
            }

            case R.id.context_menu_app_add_to_desktop: {

                break;
            }
        }

        return true;
    }
}
