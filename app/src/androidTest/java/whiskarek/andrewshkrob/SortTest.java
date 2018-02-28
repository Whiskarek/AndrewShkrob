package whiskarek.andrewshkrob;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static whiskarek.andrewshkrob.SortTest.APP_INFOS.APP_INFO_1;
import static whiskarek.andrewshkrob.SortTest.APP_INFOS.APP_INFO_2;
import static whiskarek.andrewshkrob.SortTest.APP_INFOS.APP_INFO_3;
import static whiskarek.andrewshkrob.SortTest.APP_INFOS.APP_INFO_4;

@RunWith(AndroidJUnit4.class)
public class SortTest {

    private final Context mContext = InstrumentationRegistry.getTargetContext();

    private static String SHARED_PREFERENCES_KEY;

    static final class APP_INFOS {
        static final AppInfo APP_INFO_1 = new AppInfo(
                "aaaa",
                "AAAA",
                11111,
                10,
                false,
                null,
                null
        );

        static final AppInfo APP_INFO_2 = new AppInfo(
                "bbbb",
                "BBBB",
                22222,
                20,
                false,
                null,
                null
        );

        static final AppInfo APP_INFO_3 = new AppInfo(
                "cccc",
                "CCCC",
                33333,
                30,
                false,
                null,
                null
        );

        static final AppInfo APP_INFO_4 = new AppInfo(
                "dddd",
                "DDDD",
                44444,
                40,
                false,
                null,
                null
        );
    }

    private static final List<AppInfo> APP_INFO_LIST = Arrays.asList(
            APP_INFO_1,
            APP_INFO_4,
            APP_INFO_3,
            APP_INFO_2
    );

    @Before
    public void loadSharedPreferencesKeyFromResource() {
        SHARED_PREFERENCES_KEY = mContext
                .getResources()
                .getString(R.string.pref_key_sort_type);
    }

    @Test
    public void checkSortWithContext() {
        // Check sortType = 0
        List<AppInfo> sortType = new ArrayList<>(APP_INFO_LIST);
        setSortType(mContext, 0);
        Sort.sort(sortType, mContext);

        assertEquals(APP_INFO_LIST.get(0), sortType.get(0));
        assertEquals(APP_INFO_LIST.get(1), sortType.get(1));
        assertEquals(APP_INFO_LIST.get(2), sortType.get(2));
        assertEquals(APP_INFO_LIST.get(3), sortType.get(3));
        //------------------------------------------------------------------------------------------

        // Check sortType = 1
        sortType = new ArrayList<>(APP_INFO_LIST);
        setSortType(mContext, 1);
        Sort.sort(sortType, mContext);

        assertEquals(APP_INFO_LIST.get(0), sortType.get(0));
        assertEquals(APP_INFO_LIST.get(3), sortType.get(1));
        assertEquals(APP_INFO_LIST.get(2), sortType.get(2));
        assertEquals(APP_INFO_LIST.get(1), sortType.get(3));
        //------------------------------------------------------------------------------------------

        // Check sortType = 2
        sortType = new ArrayList<>(APP_INFO_LIST);
        setSortType(mContext, 2);
        Sort.sort(sortType, mContext);

        assertEquals(APP_INFO_LIST.get(1), sortType.get(0));
        assertEquals(APP_INFO_LIST.get(2), sortType.get(1));
        assertEquals(APP_INFO_LIST.get(3), sortType.get(2));
        assertEquals(APP_INFO_LIST.get(0), sortType.get(3));
        //------------------------------------------------------------------------------------------

        // Check sortType = 3
        sortType = new ArrayList<>(APP_INFO_LIST);
        setSortType(mContext, 3);
        Sort.sort(sortType, mContext);

        assertEquals(APP_INFO_LIST.get(0), sortType.get(0));
        assertEquals(APP_INFO_LIST.get(3), sortType.get(1));
        assertEquals(APP_INFO_LIST.get(2), sortType.get(2));
        assertEquals(APP_INFO_LIST.get(1), sortType.get(3));
        //------------------------------------------------------------------------------------------

        // Check sortType = 4
        sortType = new ArrayList<>(APP_INFO_LIST);
        setSortType(mContext, 4);
        Sort.sort(sortType, mContext);

        assertEquals(APP_INFO_LIST.get(1), sortType.get(0));
        assertEquals(APP_INFO_LIST.get(2), sortType.get(1));
        assertEquals(APP_INFO_LIST.get(3), sortType.get(2));
        assertEquals(APP_INFO_LIST.get(0), sortType.get(3));
        //------------------------------------------------------------------------------------------

        // Check sortType = 5
        sortType = new ArrayList<>(APP_INFO_LIST);
        setSortType(mContext, 5);
        Sort.sort(sortType, mContext);

        assertEquals(APP_INFO_LIST.get(0), sortType.get(0));
        assertEquals(APP_INFO_LIST.get(3), sortType.get(1));
        assertEquals(APP_INFO_LIST.get(2), sortType.get(2));
        assertEquals(APP_INFO_LIST.get(1), sortType.get(3));
        //------------------------------------------------------------------------------------------

        // Check sortType = 6
        sortType = new ArrayList<>(APP_INFO_LIST);
        setSortType(mContext, 6);
        Sort.sort(sortType, mContext);

        assertEquals(APP_INFO_LIST.get(1), sortType.get(0));
        assertEquals(APP_INFO_LIST.get(2), sortType.get(1));
        assertEquals(APP_INFO_LIST.get(3), sortType.get(2));
        assertEquals(APP_INFO_LIST.get(0), sortType.get(3));
        //------------------------------------------------------------------------------------------

    }

    private static void setSortType(final Context context, final int sortType) {
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(SHARED_PREFERENCES_KEY, "" + sortType).apply();
    }
}
