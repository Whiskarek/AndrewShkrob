package whiskarek.andrewshkrob.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.InstalledApplicationsParser;
import whiskarek.andrewshkrob.LauncherExecutors;
import whiskarek.andrewshkrob.database.converter.DrawableConverter;
import whiskarek.andrewshkrob.database.converter.IntentConverter;
import whiskarek.andrewshkrob.database.dao.ApplicationInfoDao;
import whiskarek.andrewshkrob.database.entity.ApplicationEntity;

import static whiskarek.andrewshkrob.InstalledApplicationsParser.isSystemApp;

@Database(entities = {ApplicationEntity.class}, version = 1)
@TypeConverters({IntentConverter.class, DrawableConverter.class})
public abstract class LauncherDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "Launcher.db";

    public static final String DATABASE_APPS_NAME = "Applications";
    public static final String DATABASE_ROW_PACKAGE_NAME = "PackageName";
    public static final String DATABASE_ROW_INSTALL_TIME = "InstallTime";
    public static final String DATABASE_ROW_LAUNCH_AMOUNT = "LaunchAmount";
    public static final String DATABASE_ROW_IS_SYSTEM = "IsSystem";
    public static final String DATABASE_ROW_INTENT = "Intent";
    public static final String DATABASE_ROW_ICON_PATH = "IconPath";
    public static final String DATABASE_ROW_APP_NAME = "Label";

    private static boolean mFirstLaunch = false;

    private static LauncherDatabase sInstance;

    public abstract ApplicationInfoDao applicationInfoDao();

    public static LauncherDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (LauncherDatabase.class) {
                if (sInstance == null) {
                    new DrawableConverter(context.getResources(), context.getFilesDir().toString());
                    sInstance = Room.databaseBuilder(
                            context,
                            LauncherDatabase.class,
                            DATABASE_NAME
                    ).addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull final SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            mFirstLaunch = true;
                            LauncherExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    LauncherDatabase.getInstance(context).firstLoad(context);
                                }
                            });
                        }

                        @Override
                        public void onOpen(@NonNull final SupportSQLiteDatabase db) {
                            if (mFirstLaunch) {
                                return;
                            }
                            LauncherExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    LauncherDatabase.getInstance(context)
                                            .updateDatabase(context);
                                }
                            });

                            super.onOpen(db);
                        }
                    }).build();
                }
            }
        }

        return sInstance;
    }

    private void updateDatabase(final Context context) {
        if (applicationInfoDao().count() == 0) {
            firstLoad(context);
        } else {
            generateUpToDateInformation(context);
        }
    }

    private void generateUpToDateInformation(final Context context) {
        final List<String> appsFromSystem =
                InstalledApplicationsParser.getInstalledPackages(context);
        final List<String> appsFromDatabase = applicationInfoDao().loadAllPackages();

        final List<String> uninstalledPackageList = getUninstalledPackageList(
                appsFromSystem,
                appsFromDatabase
        );

        for (final String packageName : uninstalledPackageList) {
            final String iconPath = applicationInfoDao().getIconPath(packageName);
            new File(context.getFilesDir().toString() +
                    File.separator + DrawableConverter.ICON_FOLDER_NAME +
                    File.separator + iconPath
            ).delete();
            applicationInfoDao().delete(packageName);
        }

        //FIXME Instead of packages need to check intents
        final List<String> installedPackageList = getInstalledPackageList(
                appsFromSystem,
                appsFromDatabase
        );

        for (String packageName : installedPackageList) {
            final PackageManager packageManager = context.getPackageManager();
            final Intent intent = new Intent();
            intent.setPackage(packageName);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            final ResolveInfo resolveInfo = packageManager.resolveActivity(intent, 0);

            final ApplicationEntity app = InstalledApplicationsParser
                    .getApplicationInfoEntity(packageManager, resolveInfo);

            applicationInfoDao().insert(app);
        }

    }

    private static List<String> getUninstalledPackageList(
            final List<String> appsFromSystem,
            final List<String> appsFromDatabase) {

        final List<String> uninstalledAppList = new ArrayList<>(appsFromDatabase);

        for (String databaseApp : appsFromDatabase) {
            boolean installed = false;
            for (int i = 0; i < appsFromSystem.size(); i++) {
                if (databaseApp.equals(appsFromSystem.get(i))) {
                    installed = true;
                    break;
                }
            }

            if (installed) {
                uninstalledAppList.remove(databaseApp);
            }
        }

        return uninstalledAppList;
    }

    private static List<String> getInstalledPackageList(
            final List<String> appsFromSystem,
            final List<String> appsFromDatabase) {
        final List<String> installedPackageList = new ArrayList<>(appsFromSystem);

        for (String app : appsFromDatabase) {
            boolean deleted = false;
            for (int i = 0; i < appsFromSystem.size(); i++) {
                if (!app.equals(appsFromSystem.get(i))) {
                    deleted = true;
                    break;
                }
            }
            if (deleted) {
                installedPackageList.remove(app);
            }
        }

        return installedPackageList;
    }

    private void firstLoad(final Context context) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        final List<ResolveInfo> appResolveInfoList =
                packageManager.queryIntentActivities(intent, PackageManager.GET_META_DATA);

        for (ResolveInfo appInfo : appResolveInfoList) {
            if (appInfo.activityInfo.packageName.equals(context.getPackageName())) {
                continue;
            }

            final ComponentName name = new ComponentName(
                    appInfo.activityInfo.packageName,
                    appInfo.activityInfo.name
            );
            final Intent appIntent = new Intent(Intent.ACTION_MAIN);
            appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            appIntent.setComponent(name);

            final int launchAmount = 0;
            long installTime;
            try {
                installTime = packageManager
                        .getPackageInfo(appInfo.activityInfo.packageName, 0).firstInstallTime;
            } catch (PackageManager.NameNotFoundException e) {
                installTime = System.currentTimeMillis();
                Log.e("Launcher", e.toString());
            }

            final boolean systemApp = isSystemApp(packageManager, appInfo.activityInfo.packageName);

            final Drawable icon = appInfo.loadIcon(packageManager);
            final String label = appInfo.loadLabel(packageManager).toString();

            applicationInfoDao().insert(new ApplicationEntity(
                    appInfo.activityInfo.packageName,
                    installTime,
                    launchAmount,
                    systemApp,
                    appIntent,
                    icon,
                    label
            ));
        }
    }

}
