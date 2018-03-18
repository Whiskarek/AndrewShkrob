package whiskarek.andrewshkrob;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import whiskarek.andrewshkrob.database.entity.ApplicationEntity;

public class Sort {

    private static final Comparator<ApplicationEntity> mSortByInstallTimeUp =
            new Comparator<ApplicationEntity>() {
                @Override
                public int compare(final ApplicationEntity o1, final ApplicationEntity o2) {
                    return (o1.getInstallTime() < o2.getInstallTime() ? -1 :
                            (o1.getInstallTime() == o2.getInstallTime() ? 0 : 1));
                }
            };

    private static final Comparator<ApplicationEntity> mSortByInstallTimeDown =
            new Comparator<ApplicationEntity>() {
                @Override
                public int compare(final ApplicationEntity o1, final ApplicationEntity o2) {
                    return (o1.getInstallTime() > o2.getInstallTime() ? -1 :
                            (o1.getInstallTime() == o2.getInstallTime() ? 0 : 1));
                }
            };

    private static final Comparator<ApplicationEntity> mSortByNameUp =
            new Comparator<ApplicationEntity>() {
                @Override
                public int compare(final ApplicationEntity o1, final ApplicationEntity o2) {
                    return o1.getLabel().compareTo(o2.getLabel());
                }
            };

    private static final Comparator<ApplicationEntity> mSortByNameDown =
            new Comparator<ApplicationEntity>() {
                @Override
                public int compare(final ApplicationEntity o1, final ApplicationEntity o2) {
                    return o2.getLabel().compareTo(o1.getLabel());
                }
            };

    private static final Comparator<ApplicationEntity> mSortByLaunchAmountUp =
            new Comparator<ApplicationEntity>() {
                @Override
                public int compare(final ApplicationEntity o1, final ApplicationEntity o2) {
                    return (o1.getLaunchAmount() > o2.getLaunchAmount() ? 1 :
                            (o1.getLaunchAmount() == o2.getLaunchAmount() ? 0 : -1));
                }
            };

    private static final Comparator<ApplicationEntity> mSortByLaunchAmountDown =
            new Comparator<ApplicationEntity>() {
                @Override
                public int compare(final ApplicationEntity o1, final ApplicationEntity o2) {
                    return (o1.getLaunchAmount() > o2.getLaunchAmount() ? -1 :
                            (o1.getLaunchAmount() == o2.getLaunchAmount() ? 0 : 1));
                }
            };

    public static void sort(final List<ApplicationEntity> apps, final int sortType) {
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

    public static List<ApplicationEntity> getMostUsed(final List<ApplicationEntity> apps) {
        final List<ApplicationEntity> sorted = new ArrayList<>(apps);

        Collections.sort(sorted, mSortByLaunchAmountDown);

        sorted.subList(5, sorted.size()).clear();

        return sorted;
    }

}
