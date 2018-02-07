package whiskarek.andrewshkrob.activity.main;

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
    private final long mAppInstalled;
    private int mLaunchAmount;

    public Application(@NonNull final String packageName, @NonNull final String appName,
                       @Nullable Drawable appIcon, final Intent launchIntent, final long appInstalled,
                       final int launchAmount) {
        mAppName = appName;
        mPackageName = packageName;
        mAppIcon = appIcon;
        mLaunchIntent = launchIntent;
        mAppInstalled = appInstalled;
        mLaunchAmount = launchAmount;
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

    public long getAppInstalled() {
        return mAppInstalled;
    }

    public void setLaunchAmount(int launchNumber) {
        mLaunchAmount = launchNumber;
    }

    public void addLaunch() {
        mLaunchAmount++;
    }
}
