package whiskarek.andrewshkrob;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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

    public AppInfo(@NonNull final String packageName, final String applicationName,
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

    public Intent getIntent() {
        return mIntent;
    }

    public Drawable getApplicationIcon() {
        return mApplicationIcon;
    }

    public ApplicationInfoEntity getApplicationInfoEntity() {
        return new ApplicationInfoEntity(
                mPackageName,
                mInstallTime,
                mLaunchAmount,
                mSystemApp,
                mIntent.toUri(0)
        );
    }

    public static class Converter {
        public static AppInfo getAppInfo(final ApplicationInfoEntity applicationInfoEntity,
                                         final Context context) {
            final String packageName = applicationInfoEntity.getPackageName();
            final long installTime = applicationInfoEntity.getInstallTime();
            final int launchAmount = applicationInfoEntity.getLaunchAmount();
            final boolean systemApp = applicationInfoEntity.isSystemApp();
            Intent intent = null;
            try {
                intent = Intent.parseUri(applicationInfoEntity.getIntent(), 0);
            } catch (URISyntaxException e) {
                Log.e("Launcher", e.toString());
            }
            final PackageManager packageManager = context.getPackageManager();
            final ResolveInfo info = packageManager.resolveActivity(intent, 0);
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
        }

        public static List<AppInfo> getAppInfoList(
                final List<ApplicationInfoEntity> applicationInfoEntityList,
                final Context context) {
            final List<AppInfo> appInfoList = new ArrayList<>();
            for (ApplicationInfoEntity applicationInfoEntity : applicationInfoEntityList) {
                appInfoList.add(getAppInfo(applicationInfoEntity, context));
            }

            return appInfoList;
        }
    }
}
