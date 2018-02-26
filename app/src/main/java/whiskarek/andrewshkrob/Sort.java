package whiskarek.andrewshkrob;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sort {

    private static final Comparator<AppInfo> mSortByInstallTimeUp =
            new Comparator<AppInfo>() {
                @Override
                public int compare(final AppInfo o1, final AppInfo o2) {
                    return (o1.getInstallTime() < o2.getInstallTime() ? -1 :
                            (o1.getInstallTime() == o2.getInstallTime() ? 0 : 1));
                }
            };

    private static final Comparator<AppInfo> mSortByInstallTimeDown =
            new Comparator<AppInfo>() {
                @Override
                public int compare(final AppInfo o1, final AppInfo o2) {
                    return (o1.getInstallTime() > o2.getInstallTime() ? -1 :
                            (o1.getInstallTime() == o2.getInstallTime() ? 0 : 1));
                }
            };

    private static final Comparator<AppInfo> mSortByNameUp =
            new Comparator<AppInfo>() {
                @Override
                public int compare(final AppInfo o1, final AppInfo o2) {
                    return o1.getAppName().compareTo(o2.getAppName());
                }
            };

    private static final Comparator<AppInfo> mSortByNameDown =
            new Comparator<AppInfo>() {
                @Override
                public int compare(final AppInfo o1, final AppInfo o2) {
                    return o2.getAppName().compareTo(o1.getAppName());
                }
            };

    private static final Comparator<AppInfo> mSortByLaunchAmountUp =
            new Comparator<AppInfo>() {
                @Override
                public int compare(final AppInfo o1, final AppInfo o2) {
                    return (o1.getLaunchAmount() > o2.getLaunchAmount() ? -1 :
                            (o1.getLaunchAmount() == o2.getLaunchAmount() ? 0 : 1));
                }
            };

    private static final Comparator<AppInfo> mSortByLaunchAmountDown =
            new Comparator<AppInfo>() {
                @Override
                public int compare(final AppInfo o1, final AppInfo o2) {
                    return (o1.getLaunchAmount() > o2.getLaunchAmount() ? -1 :
                            (o1.getLaunchAmount() == o2.getLaunchAmount() ? 0 : 1));
                }
            };

    public static void sort(final List<AppInfo> apps, final Context context) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        final int sortType = Integer.parseInt(sharedPreferences
                .getString(context.getResources().getString(R.string.pref_key_sort_type),
                        "0"));

        switch (sortType) {
            case 0: {
                break;
            }
            case 1: {
                Collections.sort(apps, mSortByInstallTimeUp);
                break;
            }
            case 2: {
                Collections.sort(apps, mSortByInstallTimeDown);
                break;
            }
            case 3: {
                Collections.sort(apps, mSortByNameUp);
                break;
            }
            case 4: {
                Collections.sort(apps, mSortByNameDown);
                break;
            }
            case 5: {
                Collections.sort(apps, mSortByLaunchAmountUp);
                break;
            }
            case 6: {
                Collections.sort(apps, mSortByLaunchAmountDown);
                break;
            }
        }
    }

}
