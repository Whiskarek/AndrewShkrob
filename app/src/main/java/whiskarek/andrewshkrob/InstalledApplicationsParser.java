package whiskarek.andrewshkrob;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.database.entity.ApplicationEntity;

public class InstalledApplicationsParser {

    public static boolean isSystemApp(final PackageManager manager, final String packageName) {
        final ApplicationInfo info;

        try {
            info = manager.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("Launcher", e.toString());
            return false;
        }

        // Update apps are stored not in /system/app but in /data/app
        // So additionally we check whether was system app updated so won't delete it also
        int mask = ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
        return (info.flags & mask) != 0;
    }

    @NonNull
    public static ApplicationEntity getApplicationInfoEntity(
            final PackageManager packageManager,
            final ResolveInfo appInfo) {

        final ComponentName name = new ComponentName(
                appInfo.activityInfo.packageName,
                appInfo.activityInfo.name
        );
        final Intent appIntent = new Intent(Intent.ACTION_MAIN);

        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        appIntent.setComponent(name);

        final int launchAmount = 0;
        long installTime;
        try {
            installTime = packageManager
                    .getPackageInfo(appInfo.activityInfo.packageName, 0).firstInstallTime;
        } catch (PackageManager.NameNotFoundException e) {
            installTime = System.currentTimeMillis();
            Log.e("Launcher", e.toString());
        }

        final boolean systemApp = isSystemApp(packageManager, appInfo.activityInfo.packageName);
        final Drawable icon = appInfo.loadIcon(packageManager);
        final String label = appInfo.loadLabel(packageManager).toString();

        return new ApplicationEntity(
                appInfo.activityInfo.packageName,
                installTime,
                launchAmount,
                systemApp,
                appIntent,
                icon,
                label
        );
    }

    public static List<ApplicationEntity> getInstalled(final Context context) {
        final PackageManager manager = context.getPackageManager();
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> appResolveInfos =
                manager.queryIntentActivities(intent, PackageManager.GET_META_DATA);

        final List<ApplicationEntity> appInfoList = new ArrayList<>();

        for (ResolveInfo appInfo : appResolveInfos) {
            if (appInfo.activityInfo.packageName.equals(context.getPackageName())) {
                continue;
            }

            appInfoList.add(getApplicationInfoEntity(manager, appInfo));
        }

        return appInfoList;
    }

    public static List<String> getInstalledPackages(final Context context) {
        final PackageManager manager = context.getPackageManager();
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> appResolveInfoList =
                manager.queryIntentActivities(intent, PackageManager.GET_META_DATA);

        final List<String> appInfoList = new ArrayList<>();

        for (ResolveInfo appInfo : appResolveInfoList) {
            if (appInfo.activityInfo.packageName.equals(context.getPackageName())) {
                continue;
            }

            appInfoList.add(appInfo.activityInfo.packageName);
        }

        return appInfoList;
    }
}
