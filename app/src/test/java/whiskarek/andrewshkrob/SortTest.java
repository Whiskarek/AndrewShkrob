package whiskarek.andrewshkrob;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static whiskarek.andrewshkrob.SortTest.APP_INFOS.APP_INFO_1;
import static whiskarek.andrewshkrob.SortTest.APP_INFOS.APP_INFO_2;
import static whiskarek.andrewshkrob.SortTest.APP_INFOS.APP_INFO_3;
import static whiskarek.andrewshkrob.SortTest.APP_INFOS.APP_INFO_4;

@RunWith(JUnit4.class)
public class SortTest {

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

    @Test
    public void checkSort() {
        // Check sortType = 0
        List<AppInfo> sortType = new ArrayList<>(APP_INFO_LIST);
        Sort.sort(sortType, 0);

        assertEquals(APP_INFO_LIST.get(0), sortType.get(0));
        assertEquals(APP_INFO_LIST.get(1), sortType.get(1));
        assertEquals(APP_INFO_LIST.get(2), sortType.get(2));
        assertEquals(APP_INFO_LIST.get(3), sortType.get(3));
        //------------------------------------------------------------------------------------------

        // Check sortType = 1
        sortType = new ArrayList<>(APP_INFO_LIST);
        Sort.sort(sortType, 1);

        assertEquals(APP_INFO_LIST.get(0), sortType.get(0));
        assertEquals(APP_INFO_LIST.get(3), sortType.get(1));
        assertEquals(APP_INFO_LIST.get(2), sortType.get(2));
        assertEquals(APP_INFO_LIST.get(1), sortType.get(3));
        //------------------------------------------------------------------------------------------

        // Check sortType = 2
        sortType = new ArrayList<>(APP_INFO_LIST);
        Sort.sort(sortType, 2);

        assertEquals(APP_INFO_LIST.get(1), sortType.get(0));
        assertEquals(APP_INFO_LIST.get(2), sortType.get(1));
        assertEquals(APP_INFO_LIST.get(3), sortType.get(2));
        assertEquals(APP_INFO_LIST.get(0), sortType.get(3));
        //------------------------------------------------------------------------------------------

        // Check sortType = 3
        sortType = new ArrayList<>(APP_INFO_LIST);
        Sort.sort(sortType, 3);

        assertEquals(APP_INFO_LIST.get(0), sortType.get(0));
        assertEquals(APP_INFO_LIST.get(3), sortType.get(1));
        assertEquals(APP_INFO_LIST.get(2), sortType.get(2));
        assertEquals(APP_INFO_LIST.get(1), sortType.get(3));
        //------------------------------------------------------------------------------------------

        // Check sortType = 4
        sortType = new ArrayList<>(APP_INFO_LIST);
        Sort.sort(sortType, 4);

        assertEquals(APP_INFO_LIST.get(1), sortType.get(0));
        assertEquals(APP_INFO_LIST.get(2), sortType.get(1));
        assertEquals(APP_INFO_LIST.get(3), sortType.get(2));
        assertEquals(APP_INFO_LIST.get(0), sortType.get(3));
        //------------------------------------------------------------------------------------------

        // Check sortType = 5
        sortType = new ArrayList<>(APP_INFO_LIST);
        Sort.sort(sortType, 5);

        assertEquals(APP_INFO_LIST.get(0), sortType.get(0));
        assertEquals(APP_INFO_LIST.get(3), sortType.get(1));
        assertEquals(APP_INFO_LIST.get(2), sortType.get(2));
        assertEquals(APP_INFO_LIST.get(1), sortType.get(3));
        //------------------------------------------------------------------------------------------

        // Check sortType = 6
        sortType = new ArrayList<>(APP_INFO_LIST);
        Sort.sort(sortType, 6);

        assertEquals(APP_INFO_LIST.get(1), sortType.get(0));
        assertEquals(APP_INFO_LIST.get(2), sortType.get(1));
        assertEquals(APP_INFO_LIST.get(3), sortType.get(2));
        assertEquals(APP_INFO_LIST.get(0), sortType.get(3));
        //------------------------------------------------------------------------------------------

    }

}
