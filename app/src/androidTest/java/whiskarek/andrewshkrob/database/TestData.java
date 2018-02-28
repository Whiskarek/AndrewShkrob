package whiskarek.andrewshkrob.database;

import java.util.Arrays;
import java.util.List;

import whiskarek.andrewshkrob.database.entity.ApplicationInfoEntity;

/**
 * Utility class that holds values to be used for testing.
 */
public class TestData {

    static final ApplicationInfoEntity APPLICATION_INFO_ENTITY1 =
            new ApplicationInfoEntity(
                    "com.android.browser",
                    100500,
                    10,
                    true,
                    "intent1");

    static final ApplicationInfoEntity APPLICATION_INFO_ENTITY2 =
            new ApplicationInfoEntity(
            "com.android.camera",
            1337,
            2,
            false,
            "intent2");

    static final ApplicationInfoEntity APPLICATION_INFO_ENTITY3 =
            new ApplicationInfoEntity(
                    "com.example.calc",
                    200,
                    150,
                    true,
                    "intent2");

    static final List<ApplicationInfoEntity> APPLICATIONS =
            Arrays.asList(APPLICATION_INFO_ENTITY1, APPLICATION_INFO_ENTITY2);

    static final List<ApplicationInfoEntity> APPLICATIONS_WITH_SAME_INTENT =
            Arrays.asList(APPLICATION_INFO_ENTITY2, APPLICATION_INFO_ENTITY3);

}
