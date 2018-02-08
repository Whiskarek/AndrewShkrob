package whiskarek.andrewshkrob;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Application {

    private final String mPackageName;
    private final String mAppName;
    @Nullable
    private final Drawable mAppIcon;
    private final Intent mLaunchIntent;
    private final long mInstallTime;
    private int mLaunchAmount;
    private final boolean mSystemApp;

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

    public String getAppName() {
        return mAppName;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public @Nullable
    Drawable getAppIcon() {
        return mAppIcon;
    }

    public Intent getLaunchIntent() {
        return mLaunchIntent;
    }

    public int getLaunchAmount() {
        return mLaunchAmount;
    }

    public long getInstallTime() {
        return mInstallTime;
    }

    public void setLaunchAmount(int launchNumber) {
        mLaunchAmount = launchNumber;
    }

    public void addLaunch() {
        mLaunchAmount++;
    }

    public boolean isSystemApp() {
        return mSystemApp;
    }
}
