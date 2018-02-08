package whiskarek.andrewshkrob.activity.database;

import android.provider.BaseColumns;

public interface Database {

    String TABLE_NAME = "Applications";

    interface Columns extends BaseColumns {
        String FIELD_APP_PACKAGE_NAME = "PackageName";
        String FIELD_APP_LAUNCH_INTENT = "LaunchIntent";
        String FIELD_APP_INSTALL_TIME = "InstallTime";
        String FIELD_APP_LAUNCH_AMOUNT = "LaunchAmount";
        String FIELD_APP_IS_SYSTEM = "IsSystem";
    }

    String CREATE_TABLE_SCRIPT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    "(" +
                    Columns.FIELD_APP_PACKAGE_NAME + " TEXT, " +
                    Columns.FIELD_APP_LAUNCH_INTENT + " TEXT, " +
                    Columns.FIELD_APP_INSTALL_TIME + " INTEGER, " +
                    Columns.FIELD_APP_LAUNCH_AMOUNT + " INTEGER, " +
                    Columns.FIELD_APP_IS_SYSTEM + " INTEGER, " +
                    "UNIQUE (" + Columns.FIELD_APP_PACKAGE_NAME + ") ON CONFLICT REPLACE" +
                    ");";

    String DROP_TABLE_SCRIPT =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

}
