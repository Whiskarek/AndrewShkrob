package whiskarek.andrewshkrob.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import whiskarek.andrewshkrob.model.ApplicationEntityModel;

@Entity(tableName = "Applications",
        indices = {@Index(value = "PackageName", unique = true)})
public class ApplicationEntity implements ApplicationEntityModel {

    @ColumnInfo(name = "PackageName")
    @PrimaryKey
    @NonNull
    private String mPackageName;

    @ColumnInfo(name = "InstallTime")
    private long mInstallTime;

    @ColumnInfo(name = "LaunchAmount")
    private int mLaunchAmount;

    @ColumnInfo(name = "LaunchIntent")
    private String mLaunchIntent;

    @ColumnInfo(name = "IsSystem")
    private boolean mSystemApp;

    public ApplicationEntity(final String packageName, final long installTime,
                             final int launchAmount, final String launchIntent, final boolean systemApp) {
        mPackageName = packageName;
        mInstallTime = installTime;
        mLaunchAmount = launchAmount;
        mLaunchIntent = launchIntent;
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
    public String getLaunchIntent() {
        return mLaunchIntent;
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
