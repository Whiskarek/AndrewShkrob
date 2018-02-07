package whiskarek.andrewshkrob.activity.main.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import whiskarek.andrewshkrob.activity.main.ApplicationsLoader;

public class InstalledAppsReceiver extends BroadcastReceiver {

    private final ApplicationsLoader mApplicationsLoader;

    public InstalledAppsReceiver(final ApplicationsLoader applicationsLoader) {
        mApplicationsLoader = applicationsLoader;

        IntentFilter filter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");
        mApplicationsLoader.getContext().registerReceiver(this, filter);
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.i("BROADCAST", "Broadcast: " + intent.toString());
        mApplicationsLoader.setIntent(intent);
        mApplicationsLoader.onContentChanged();
    }
}
