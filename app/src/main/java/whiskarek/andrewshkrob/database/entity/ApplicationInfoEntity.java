package whiskarek.andrewshkrob.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import whiskarek.andrewshkrob.database.LauncherDatabase;
import whiskarek.andrewshkrob.model.ApplicationInfoModel;

@Entity(tableName = LauncherDatabase.DATABASE_NAME,
        indices = {@Index(value = LauncherDatabase.DATABASE_ROW_PACKAGE_NAME,
                          unique = true)})
public class ApplicationInfoEntity implements ApplicationInfoModel{

    @ColumnInfo(name = LauncherDatabase.DATABASE_ROW_PACKAGE_NAME)
    @PrimaryKey
    @NonNull
    private String mPackageName;

    @ColumnInfo(name = LauncherDatabase.DATABASE_ROW_INSTALL_TIME)
    private long mInstallTime;

    @ColumnInfo(name = LauncherDatabase.DATABASE_ROW_LAUNCH_AMOUNT)
    private int mLaunchAmount;

    @ColumnInfo(name = LauncherDatabase.DATABASE_ROW_IS_SYSTEM)
    private boolean mSystemApp;

    public ApplicationInfoEntity(final String packageName, final long installTime,
                             final int launchAmount, final boolean systemApp) {
        mPackageName = packageName;
        mInstallTime = installTime;
        mLaunchAmount = launchAmount;
        mSystemApp = systemApp;
    }

    @Override
    public String getPackageName() {
        return mPackageName;
    }

    @Override
    public long getInstallTime() {
        return mInstallTime;
    }

    @Override
    public int getLaunchAmount() {
        return mLaunchAmount;
    }

    @Override
    public boolean isSystemApp() {
        return mSystemApp;
    }

}
