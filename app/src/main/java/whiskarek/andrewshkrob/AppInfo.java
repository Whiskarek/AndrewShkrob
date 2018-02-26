package whiskarek.andrewshkrob;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import whiskarek.andrewshkrob.database.entity.ApplicationInfoEntity;
import whiskarek.andrewshkrob.model.ApplicationInfoModel;

public class AppInfo implements ApplicationInfoModel {

    @NonNull
    private final String mPackageName;
    private final String mApplicationName;
    private final long mInstallTime;
    private final int mLaunchAmount;
    private final boolean mSystemApp;
    private final Drawable mApplicationIcon;

    public AppInfo(final String packageName, final String applicationName,
                   final long installTime, final int launchAmount,
                   final boolean systemApp, final Drawable applicationIcon) {
        mPackageName = packageName;
        mApplicationName = applicationName;
        mInstallTime = installTime;
        mLaunchAmount = launchAmount;
        mSystemApp = systemApp;
        mApplicationIcon = applicationIcon;
    }

    @Override
    public String getPackageName() {
        return mPackageName;
    }

    public String getAppName() {
        return mApplicationName;
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

    public Drawable getApplicationIcon() {
        return mApplicationIcon;
    }

    public ApplicationInfoEntity getApplicationInfoEntity() {
        return new ApplicationInfoEntity(mPackageName, mInstallTime, mLaunchAmount, mSystemApp);
    }
}
