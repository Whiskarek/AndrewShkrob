package whiskarek.andrewshkrob.activity.main;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import whiskarek.andrewshkrob.R;

public final class Util {

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
                        return (o1.getAppInstalled() < o2.getAppInstalled() ? -1 :
                                (o1.getAppInstalled() == o2.getAppInstalled() ? 0 : 1));
                    }
                });
                break;
            }
            case 2: {
                Collections.sort(apps, new Comparator<Application>() {
                    @Override
                    public int compare(Application o1, Application o2) {
                        return (o1.getAppInstalled() > o2.getAppInstalled() ? -1 :
                                (o1.getAppInstalled() == o2.getAppInstalled() ? 0 : 1));
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

    public static boolean isSystemApp(final Context context, final String packageName) {
        final ApplicationInfo info;
        try {
            info = context.getPackageManager().getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        int mask = ApplicationInfo.FLAG_SYSTEM | ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
        return (info.flags & mask) != 0;
    }

}
