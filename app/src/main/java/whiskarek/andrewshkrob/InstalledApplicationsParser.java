package whiskarek.andrewshkrob;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.TimingLogger;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.database.entity.ApplicationInfoEntity;

public class InstalledApplicationsParser {

    public static boolean isDefault(final Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo resolveInfo = context.getPackageManager()
                .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        String currentHomePackage = resolveInfo.activityInfo.packageName;

        return currentHomePackage.equals(context.getPackageName());
    }

    public static boolean isSystemApp(final Context context, final String packageName) {
        final ApplicationInfo info;

        try {
            info = context.getPackageManager().getApplicationInfo(packageName, 0);
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
    public static AppInfo newAppInfo(final Context context, final String packageName) {
        final PackageManager manager = context.getPackageManager();
        final Intent intent = manager.getLaunchIntentForPackage(packageName);
        final ResolveInfo info = manager.resolveActivity(intent, 0);

        final int launchAmount = 0;
        long installTime;
        try {
            installTime = manager.getPackageInfo(packageName, 0).firstInstallTime;
        } catch (PackageManager.NameNotFoundException e) {
            installTime = System.currentTimeMillis();
            Log.e("Launcher", e.toString());
        }

        final boolean systemApp = isSystemApp(context, packageName);

        final Drawable applicationIcon = info.loadIcon(manager);
        final String applicationName = info.loadLabel(manager).toString();

        return new AppInfo(
                packageName,
                applicationName,
                installTime,
                launchAmount,
                systemApp,
                applicationIcon,
                intent
        );
    }

    public static List<AppInfo> getInstalledApplications(final Context context) {
        final PackageManager manager = context.getPackageManager();
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> appResolveInfos =
                manager.queryIntentActivities(intent, PackageManager.GET_META_DATA);

        final List<AppInfo> appInfos = new ArrayList<>();

        for (ResolveInfo appInfo : appResolveInfos) {
            if (appInfo.activityInfo.packageName.equals(context.getPackageName())) {
                continue;
            }

            appInfos.add(newAppInfo(context, appInfo.activityInfo.packageName));

        }

        return appInfos;
    }

    @NonNull
    public static ApplicationInfoEntity getApplicationInfoEntity(final String packageName,
                                                                 final Context context) {
        final PackageManager manager = context.getPackageManager();
        final Intent intent = manager.getLaunchIntentForPackage(packageName);
        final ResolveInfo info = manager.resolveActivity(intent, 0);

        final int launchAmount = 0;
        long installTime;
        try {
            installTime = manager.getPackageInfo(packageName, 0).firstInstallTime;
        } catch (PackageManager.NameNotFoundException e) {
            installTime = System.currentTimeMillis();
            Log.e("Launcher", e.toString());
        }

        final boolean systemApp = isSystemApp(context, packageName);

        return new ApplicationInfoEntity(
                packageName,
                installTime,
                launchAmount,
                systemApp,
                intent.toUri(0)
        );
    }

    public static List<ApplicationInfoEntity> getInstalled(final Context context) {
        final PackageManager manager = context.getPackageManager();
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> appResolveInfos =
                manager.queryIntentActivities(intent, PackageManager.GET_META_DATA);

        final List<ApplicationInfoEntity> appInfos = new ArrayList<>();

        for (ResolveInfo appInfo : appResolveInfos) {
            if (appInfo.activityInfo.packageName.equals(context.getPackageName())) {
                continue;
            }

            appInfos.add(getApplicationInfoEntity(appInfo.activityInfo.packageName, context));
        }

        return appInfos;
    }

    public static void firstLoad(final Context context) {
        final List<ApplicationInfoEntity> appInfos = getInstalled(context);

        ((LauncherApplication) context.getApplicationContext())
                .getDatabase()
                .applicationInfoDao()
                .insertAll(appInfos);
    }

}
