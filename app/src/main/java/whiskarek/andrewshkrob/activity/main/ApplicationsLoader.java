package whiskarek.andrewshkrob.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.activity.main.broadcast.InstalledAppsReceiver;

public class ApplicationsLoader extends AsyncTaskLoader<List<Application>> {

    public static List<Application> applications;

    private final PackageManager mPackageManager;

    private List<Application> mApps;

    private InstalledAppsReceiver mAppsObserver;

    private Intent mIntent = null;

    public ApplicationsLoader(final Context context) {
        super(context);

        mPackageManager = context.getPackageManager();
    }

    @Override
    public List<Application> loadInBackground() {
        List<Application> apps = null;
        //if (mIntent == null) {
            apps = getInstalledApps();
        /*} else {
            apps = mApps;
            switch (mIntent.getAction()) {
                case Intent.ACTION_PACKAGE_ADDED: {
                    Log.i("BROADCAST", "Broadcast: " + mIntent.toString());
                    apps.add(getApplicationFromIntent());
                    mIntent = null;
                    break;
                }
                case Intent.ACTION_PACKAGE_REMOVED: {
                    Log.i("BROADCAST", "Broadcast: " + mIntent.toString());
                    apps = removeApplicationFromList(apps);
                    mIntent = null;
                    break;
                }
            }

        }*/
        return Util.sortApps(apps, getContext());
    }

    @Nullable
    private Application getApplicationFromIntent() {
        final PackageManager packageManager = getContext().getPackageManager();
        final String appPackageName = mIntent.getData().getEncodedSchemeSpecificPart();

        if (!(packageManager.getLaunchIntentForPackage(appPackageName) != null
                && !packageManager.getLaunchIntentForPackage(appPackageName).equals(""))) {
            // Do nothing
        } else {
            try {
                final ApplicationInfo app = packageManager
                        .getApplicationInfo(appPackageName, PackageManager.GET_META_DATA);
                final CharSequence appNameCharSequence = packageManager.getApplicationLabel(app);
                final String appName = (appNameCharSequence != null ? appNameCharSequence.toString()
                        : appPackageName);
                final Intent appLaunchIntent =
                        packageManager.getLaunchIntentForPackage(appPackageName);
                long appInstalled = 0L;
                Drawable appIcon = null;
                appInstalled = packageManager.getPackageInfo(app.packageName, 0)
                        .firstInstallTime;
                appIcon = packageManager.getApplicationIcon(app.packageName);
                Log.i("BROADCAST", "Item Added");
                return new Application(appPackageName, appName,
                        appIcon, appLaunchIntent, appInstalled, 0);
            } catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.e(getContext().getResources().getString(R.string.log_tag_error),
                        nameNotFoundException.toString());
            }
        }
        return null;
    }

    private List<Application> removeApplicationFromList(final List<Application> apps) {

        final String appPackageName = mIntent.getData().getEncodedSchemeSpecificPart();

        for (int i = apps.size() - 1; i >= 0; i--) {
            if (apps.get(i).getPackageName().equals(appPackageName)) {
                Log.i("BROADCAST", "Item Removed");
                apps.remove(i);
                break;
            }
        }
        return apps;
    }

    private List<Application> getInstalledApps() {
        final PackageManager packageManager = getContext().getPackageManager();
        final List<Application> apps = new ArrayList<>();

        final List<ApplicationInfo> applicationInfos =
                packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo app : applicationInfos) {
            final String appPackageName = app.packageName;

            if (!(packageManager.getLaunchIntentForPackage(appPackageName) != null
                    && !packageManager.getLaunchIntentForPackage(appPackageName).equals(""))) {
                continue;
            }

            final CharSequence appNameCharSequence = packageManager.getApplicationLabel(app);
            final String appName = (appNameCharSequence != null ? appNameCharSequence.toString()
                    : appPackageName);
            final Intent appLaunchIntent =
                    packageManager.getLaunchIntentForPackage(appPackageName);
            long appInstalled = 0L;
            Drawable appIcon = null;

            try {
                appInstalled = packageManager.getPackageInfo(app.packageName, 0)
                        .firstInstallTime;
                appIcon = packageManager.getApplicationIcon(app.packageName);
            } catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.e(getContext().getResources().getString(R.string.log_tag_error),
                        nameNotFoundException.toString());
            }

            apps.add(new Application(appPackageName, appName, appIcon,
                    appLaunchIntent, appInstalled, 0));

        }

        return apps;
    }

    @Override
    public void deliverResult(final List<Application> apps) {
        super.deliverResult(apps);
        if (isReset()) {
            if (apps != null) {
                releaseResources(apps);
                return;
            }
        }
        List<Application> oldApps = apps;
        mApps = apps;

        if (isStarted()) {
            super.deliverResult(apps);
        }

        if (oldApps != null) {
            releaseResources(oldApps);
        }
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        if (mApps != null) {
            deliverResult(mApps);
        }

        if (mAppsObserver == null) {
            mAppsObserver = new InstalledAppsReceiver(this);
        }

        if (takeContentChanged()) {
            forceLoad();
        } else if (mApps == null) {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();

        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();

        onStopLoading();
        if (mApps != null) {
            releaseResources(mApps);
            mApps = null;
        }

        if (mAppsObserver != null) {
            getContext().unregisterReceiver(mAppsObserver);
            mAppsObserver = null;
        }
    }

    @Override
    public void onCanceled(final List<Application> data) {
        super.onCanceled(data);

        releaseResources(data);
    }

    private void releaseResources(final List<Application> data) {
        data.removeAll(data);
    }

    public void setIntent(final Intent intent) {
        mIntent = intent;
    }
}
