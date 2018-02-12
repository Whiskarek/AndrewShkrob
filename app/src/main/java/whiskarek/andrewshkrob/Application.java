package whiskarek.andrewshkrob;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import whiskarek.andrewshkrob.model.ApplicationModel;

public class Application implements ApplicationModel {

    private final String mPackageName;
    private final String mAppName;
    @Nullable
    private final Drawable mAppIcon;
    private final Intent mLaunchIntent;
    private final long mInstallTime;
    private final boolean mSystemApp;
    private int mLaunchAmount;

    public Application(@NonNull final String packageName, @NonNull final String appName,
                       @Nullable Drawable appIcon, final Intent launchIntent, final long installTime,
                       final int launchAmount, boolean systemApp) {
        mAppName = appName;
        mPackageName = packageName;
        mAppIcon = appIcon;
        mLaunchIntent = launchIntent;
        mInstallTime = installTime;
        mLaunchAmount = launchAmount;
        mSystemApp = systemApp;
    }

    @Override
    public String getAppName() {
        return mAppName;
    }

    @Override
    public String getPackageName() {
        return mPackageName;
    }

    @Override
    public @Nullable
    Drawable getAppIcon() {
        return mAppIcon;
    }

    @Override
    public Intent getLaunchIntent() {
        return mLaunchIntent;
    }

    @Override
    public int getLaunchAmount() {
        return mLaunchAmount;
    }

    @Override
    public long getInstallTime() {
        return mInstallTime;
    }

    public void addLaunch() {
        mLaunchAmount++;
    }

    @Override
    public boolean isSystemApp() {
        return mSystemApp;
    }

}
