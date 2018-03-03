package whiskarek.andrewshkrob.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
    private boolean mSystem;

    @ColumnInfo(name = LauncherDatabase.DATABASE_ROW_INTENT)
    @PrimaryKey
    @NonNull
    private Intent mIntent;

    @ColumnInfo(name = LauncherDatabase.DATABASE_ROW_ICON_PATH)
    private Drawable mIcon;

    @ColumnInfo(name = LauncherDatabase.DATABASE_ROW_APP_NAME)
    private String mLabel;

    public ApplicationInfoEntity(final String packageName, final long installTime,
                                 final int launchAmount, final boolean system,
                                 @NonNull final Intent intent, final Drawable icon,
                                 final String label) {
        mPackageName = packageName;
        mInstallTime = installTime;
        mLaunchAmount = launchAmount;
        mSystem = system;
        mIntent = intent;
        mIcon = icon;
        mLabel = label;
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
    public boolean isSystem() {
        return mSystem;
    }

    @Override
    public Intent getIntent() {
        return mIntent;
    }

    @Override
    public Drawable getIcon() {
        return mIcon;
    }

    @Override
    public String getLabel() {
        return mLabel;
    }

}
