package whiskarek.andrewshkrob;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

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
    private final Intent mIntent;

    AppInfo(@NonNull final String packageName, final String applicationName,
                   final long installTime, final int launchAmount,
                   final boolean systemApp, final Drawable applicationIcon,
                   final Intent intent) {
        mPackageName = packageName;
        mApplicationName = applicationName;
        mInstallTime = installTime;
        mLaunchAmount = launchAmount;
        mSystemApp = systemApp;
        mApplicationIcon = applicationIcon;
        mIntent = intent;
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

    @Override
    public Intent getIntent() {
        return mIntent;
    }

    public Drawable getApplicationIcon() {
        return mApplicationIcon;
    }

    public static class Converter {

        @Nullable
        public static AppInfo getAppInfo(final PackageManager packageManager,
                                  final ApplicationInfoEntity applicationInfoEntity) {
            final String packageName = applicationInfoEntity.getPackageName();
            final long installTime = applicationInfoEntity.getInstallTime();
            final int launchAmount = applicationInfoEntity.getLaunchAmount();
            final boolean systemApp = applicationInfoEntity.isSystemApp();
            final Intent intent = applicationInfoEntity.getIntent();
            final ResolveInfo info = packageManager.resolveActivity(intent, 0);
            try {
                final String applicationName = info.loadLabel(packageManager).toString();
                final Drawable applicationIcon = info.loadIcon(packageManager);

                return new AppInfo(
                        packageName,
                        applicationName,
                        installTime,
                        launchAmount,
                        systemApp,
                        applicationIcon,
                        intent
                );
            } catch (NullPointerException e) {
                Log.e("Launcher", e.toString());
            }

            return null;
        }
    }
}
