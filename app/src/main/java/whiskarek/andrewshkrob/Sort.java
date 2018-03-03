package whiskarek.andrewshkrob;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import whiskarek.andrewshkrob.database.entity.ApplicationInfoEntity;

public class Sort {

    private static final Comparator<ApplicationInfoEntity> mSortByInstallTimeUp =
            new Comparator<ApplicationInfoEntity>() {
                @Override
                public int compare(final ApplicationInfoEntity o1, final ApplicationInfoEntity o2) {
                    return (o1.getInstallTime() < o2.getInstallTime() ? -1 :
                            (o1.getInstallTime() == o2.getInstallTime() ? 0 : 1));
                }
            };

    private static final Comparator<ApplicationInfoEntity> mSortByInstallTimeDown =
            new Comparator<ApplicationInfoEntity>() {
                @Override
                public int compare(final ApplicationInfoEntity o1, final ApplicationInfoEntity o2) {
                    return (o1.getInstallTime() > o2.getInstallTime() ? -1 :
                            (o1.getInstallTime() == o2.getInstallTime() ? 0 : 1));
                }
            };

    private static final Comparator<ApplicationInfoEntity> mSortByNameUp =
            new Comparator<ApplicationInfoEntity>() {
                @Override
                public int compare(final ApplicationInfoEntity o1, final ApplicationInfoEntity o2) {
                    return o1.getLabel().compareTo(o2.getLabel());
                }
            };

    private static final Comparator<ApplicationInfoEntity> mSortByNameDown =
            new Comparator<ApplicationInfoEntity>() {
                @Override
                public int compare(final ApplicationInfoEntity o1, final ApplicationInfoEntity o2) {
                    return o2.getLabel().compareTo(o1.getLabel());
                }
            };

    private static final Comparator<ApplicationInfoEntity> mSortByLaunchAmountUp =
            new Comparator<ApplicationInfoEntity>() {
                @Override
                public int compare(final ApplicationInfoEntity o1, final ApplicationInfoEntity o2) {
                    return (o1.getLaunchAmount() > o2.getLaunchAmount() ? 1 :
                            (o1.getLaunchAmount() == o2.getLaunchAmount() ? 0 : -1));
                }
            };

    private static final Comparator<ApplicationInfoEntity> mSortByLaunchAmountDown =
            new Comparator<ApplicationInfoEntity>() {
                @Override
                public int compare(final ApplicationInfoEntity o1, final ApplicationInfoEntity o2) {
                    return (o1.getLaunchAmount() > o2.getLaunchAmount() ? -1 :
                            (o1.getLaunchAmount() == o2.getLaunchAmount() ? 0 : 1));
                }
            };

    public static void sort(final List<ApplicationInfoEntity> apps, final int sortType) {
        switch (sortType) {
            case 0: {
                Collections.sort(apps, mSortByNameUp);
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
