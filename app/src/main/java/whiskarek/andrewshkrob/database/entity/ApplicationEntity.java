package whiskarek.andrewshkrob.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import whiskarek.andrewshkrob.database.ApplicationsDatabase;
import whiskarek.andrewshkrob.model.ApplicationModel;

@Entity(tableName = ApplicationsDatabase.NAME,
        indices = {@Index(value = ApplicationsDatabase.ROW_INTENT, unique = true),
                   @Index(value = ApplicationsDatabase.ROW_ID, unique = true)})
public class ApplicationEntity implements ApplicationModel {

    @ColumnInfo(name = ApplicationsDatabase.ROW_ID)
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int mId;

    @ColumnInfo(name = ApplicationsDatabase.ROW_PACKAGE_NAME)
    private String mPackageName;

    @ColumnInfo(name = ApplicationsDatabase.ROW_INSTALL_TIME)
    private long mInstallTime;

    @ColumnInfo(name = ApplicationsDatabase.ROW_LAUNCH_AMOUNT)
    private int mLaunchAmount;

    @ColumnInfo(name = ApplicationsDatabase.ROW_IS_SYSTEM)
    private boolean mSystem;

    @ColumnInfo(name = ApplicationsDatabase.ROW_INTENT)
    private Intent mIntent;

    @ColumnInfo(name = ApplicationsDatabase.ROW_ICON_PATH)
    private Drawable mIcon;

    @ColumnInfo(name = ApplicationsDatabase.ROW_APP_NAME)
    private String mLabel;

    public ApplicationEntity(final int id, final String packageName, final long installTime,
                             final int launchAmount, final boolean system,
                             @NonNull final Intent intent, final Drawable icon,
                             final String label) {
        mId = id;
        mPackageName = packageName;
        mInstallTime = installTime;
        mLaunchAmount = launchAmount;
        mSystem = system;
        mIntent = intent;
        mIcon = icon;
        mLabel = label;
    }

    @Ignore
    public ApplicationEntity(final String packageName, final long installTime,
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
    public int getId() {
        return mId;
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
