package whiskarek.andrewshkrob;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class Utils {

    public static List<Application> sortApps(final List<Application> apps, final Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        final int sortType = Integer.parseInt(sharedPreferences
                .getString(context.getResources().getString(R.string.pref_key_sort_type),
                        "0"));

        switch (sortType) {
            case 0:
                break;
            case 1: {
                Collections.sort(apps, new Comparator<Application>() {
                    @Override
                    public int compare(Application o1, Application o2) {
                        return (o1.getInstallTime() < o2.getInstallTime() ? -1 :
                                (o1.getInstallTime() == o2.getInstallTime() ? 0 : 1));
                    }
                });
                break;
            }
            case 2: {
                Collections.sort(apps, new Comparator<Application>() {
                    @Override
                    public int compare(Application o1, Application o2) {
                        return (o1.getInstallTime() > o2.getInstallTime() ? -1 :
                                (o1.getInstallTime() == o2.getInstallTime() ? 0 : 1));
                    }
                });
                break;
            }
            case 3: {
                Collections.sort(apps, new Comparator<Application>() {
                    @Override
                    public int compare(Application o1, Application o2) {
                        return o1.getAppName().compareTo(o2.getAppName());
                    }
                });
                break;
            }
            case 4: {
                Collections.sort(apps, new Comparator<Application>() {
                    @Override
                    public int compare(Application o1, Application o2) {
                        return o2.getAppName().compareTo(o1.getAppName());
                    }
                });
                break;
            }
            case 5: {
                Collections.sort(apps, new Comparator<Application>() {
                    @Override
                    public int compare(Application o1, Application o2) {
                        return (o1.getLaunchAmount() > o2.getLaunchAmount() ? -1 :
                                (o1.getLaunchAmount() == o2.getLaunchAmount() ? 0 : 1));
                    }
                });
                break;
            }
            case 6: {
                Collections.sort(apps, new Comparator<Application>() {
                    @Override
                    public int compare(Application o1, Application o2) {
                        return (o1.getLaunchAmount() > o2.getLaunchAmount() ? -1 :
                                (o1.getLaunchAmount() == o2.getLaunchAmount() ? 0 : 1));
                    }
                });
                break;
            }

        }

        return apps;
    }

    private static boolean isSystemApp(final Context context, final String packageName) {
        final ApplicationInfo info;
        try {
            info = context.getPackageManager().getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("UTIL", e.toString());
            return false;
        }
        int mask = ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
        return (info.flags & mask) != 0;
    }

    @Nullable
    public static List<Application> loadApplications(final Context context) {
        List<Application> applications = readFromSystem(context);
        applications = sortApps(applications, context);
        return applications;
    }

    private static List<Application> readFromSystem(final Context context) {
        final List<Application> applications = new ArrayList<>();
        final PackageManager manager = context.getPackageManager();
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> apps = manager.queryIntentActivities(intent, PackageManager.GET_META_DATA);

        ResolveInfo my = null;
        for (ResolveInfo info : apps) {
            if (info.activityInfo.packageName.equals(context.getPackageName())) {
                my = info;
                break;
            }
        }
        apps.remove(my);

        for (ResolveInfo info : apps) {
            applications.add(getApp(info, context));
        }

        return applications;
    }

    @NonNull
    public static Application getApp(final ResolveInfo info, final Context context) {
        final PackageManager manager = context.getPackageManager();

        final String packageName = info.activityInfo.packageName;
        final String appName = info.loadLabel(manager).toString();
        final int launchAmount = 0;
        long installTime = 0L;
        try {
            installTime = manager
                    .getPackageInfo(info.activityInfo.packageName, 0)
                    .firstInstallTime;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("UTIL", e.toString());
        }
        final boolean systemApp = isSystemApp(context, info.activityInfo.packageName);
        final ActivityInfo activity = info.activityInfo;
        final ComponentName name = new ComponentName(activity.applicationInfo.packageName,
                activity.name);
        final Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
        launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        launcherIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        launcherIntent.setComponent(name);

        final Drawable appIcon = info.loadIcon(manager);

        return new Application(
                packageName,
                appName,
                appIcon,
                launcherIntent,
                installTime,
                launchAmount,
                systemApp
        );
    }

    @Nullable
    public static Drawable getAppIcon(final Context context, final String packageName) {
        try {

            final PackageManager packageManager = context.getPackageManager();
            final Intent intent = new Intent();
            intent.setPackage(packageName);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            final ResolveInfo resolveInfo = packageManager.resolveActivity(intent, 0);

            return resolveInfo.loadIcon(packageManager);
        } catch (Exception e) {
            Log.e("SQL", e.toString());
        }
        return null;
    }

    @Nullable
    public static String getAppName(final Context context, final String packageName) {
        try {
            final PackageManager packageManager = context.getPackageManager();
            final Intent intent = new Intent();
            intent.setPackage(packageName);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            final ResolveInfo resolveInfo = packageManager.resolveActivity(intent, 0);

            return resolveInfo.loadLabel(packageManager).toString();
        } catch (Exception e) {
            Log.e("SQL", e.toString());
        }
        return null;
    }

}
