package whiskarek.andrewshkrob.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import whiskarek.andrewshkrob.database.ApplicationDatabase;
import whiskarek.andrewshkrob.model.entity.ApplicationModel;

@Entity(tableName = ApplicationDatabase.NAME,
        indices = {@Index(value = ApplicationDatabase.ROW_INTENT, unique = true),
                   @Index(value = ApplicationDatabase.ROW_ID, unique = true)})
public class ApplicationEntity implements ApplicationModel {

    @ColumnInfo(name = ApplicationDatabase.ROW_ID)
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int mId;

    @ColumnInfo(name = ApplicationDatabase.ROW_PACKAGE_NAME)
    private String mPackageName;

    @ColumnInfo(name = ApplicationDatabase.ROW_INSTALL_TIME)
    private long mInstallTime;

    @ColumnInfo(name = ApplicationDatabase.ROW_LAUNCH_AMOUNT)
    private int mLaunchAmount;

    @ColumnInfo(name = ApplicationDatabase.ROW_IS_SYSTEM)
    private boolean mSystem;

    @ColumnInfo(name = ApplicationDatabase.ROW_INTENT)
    private Intent mIntent;

    @ColumnInfo(name = ApplicationDatabase.ROW_ICON_PATH)
    private Drawable mIcon;

    @ColumnInfo(name = ApplicationDatabase.ROW_APP_NAME)
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
