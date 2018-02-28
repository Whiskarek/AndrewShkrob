package whiskarek.andrewshkrob.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import whiskarek.andrewshkrob.database.dao.ApplicationInfoDao;
import whiskarek.andrewshkrob.database.entity.ApplicationInfoEntity;

@Database(entities = {ApplicationInfoEntity.class}, version = 1)
public abstract class LauncherDatabase extends RoomDatabase{

    public static final String DATABASE_NAME = "AppInfo";

    public static final String DATABASE_ROW_PACKAGE_NAME = "PackageName";
    public static final String DATABASE_ROW_INSTALL_TIME = "InstallTime";
    public static final String DATABASE_ROW_LAUNCH_AMOUNT = "LaunchAmount";
    public static final String DATABASE_ROW_IS_SYSTEM = "IsSystem";
    public static final String DATABASE_ROW_INTENT = "Intent";

    private static LauncherDatabase sInstance;

    public abstract ApplicationInfoDao applicationInfoDao();

    public static LauncherDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (LauncherDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(
                            context,
                            LauncherDatabase.class,
                            DATABASE_NAME
                    ).build();
                }
            }
        }

        return sInstance;
    }
}
