package whiskarek.andrewshkrob.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;
import android.support.annotation.NonNull;

import whiskarek.andrewshkrob.database.LauncherDatabase;
import whiskarek.andrewshkrob.model.ApplicationInfoModel;

@Entity(tableName = LauncherDatabase.DATABASE_APPS_NAME,
        indices = {@Index(value = LauncherDatabase.DATABASE_ROW_INTENT,
                unique = true)})
public class ApplicationInfoEntity implements ApplicationInfoModel {

    @ColumnInfo(name = LauncherDatabase.DATABASE_ROW_PACKAGE_NAME)
    private String mPackageName;

    @ColumnInfo(name = LauncherDatabase.DATABASE_ROW_INSTALL_TIME)
    private long mInstallTime;

    @ColumnInfo(name = LauncherDatabase.DATABASE_ROW_LAUNCH_AMOUNT)
    private int mLaunchAmount;

    @ColumnInfo(name = LauncherDatabase.DATABASE_ROW_IS_SYSTEM)
    private boolean mSystemApp;

    @ColumnInfo(name = LauncherDatabase.DATABASE_ROW_INTENT)
    @PrimaryKey
    @NonNull
    private Intent mIntent;

    public ApplicationInfoEntity(final String packageName, final long installTime,
                                 final int launchAmount, final boolean systemApp,
                                 final Intent intent) {
        mPackageName = packageName;
        mInstallTime = installTime;
        mLaunchAmount = launchAmount;
        mSystemApp = systemApp;
        mIntent = intent;
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

    @Override
    public Intent getIntent() {
        return mIntent;
    }

}
