package whiskarek.andrewshkrob.activity.main.fragments.launcher;


import android.content.Context;
import android.content.SharedPreferences;
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

}
