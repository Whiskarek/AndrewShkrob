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
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.InstalledApplicationsParser;
import whiskarek.andrewshkrob.LauncherExecutors;
import whiskarek.andrewshkrob.database.converter.IntentConverter;
import whiskarek.andrewshkrob.database.dao.ApplicationInfoDao;
import whiskarek.andrewshkrob.database.entity.ApplicationInfoEntity;

import static whiskarek.andrewshkrob.InstalledApplicationsParser.isSystemApp;

@Database(entities = {ApplicationInfoEntity.class}, version = 1)
@TypeConverters({IntentConverter.class})
public abstract class LauncherDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "Launcher.db";
    public static final String DATABASE_APPS_NAME = "Applications";

    public static final String DATABASE_ROW_PACKAGE_NAME = "PackageName";
    public static final String DATABASE_ROW_INSTALL_TIME = "InstallTime";
    public static final String DATABASE_ROW_LAUNCH_AMOUNT = "LaunchAmount";
    public static final String DATABASE_ROW_IS_SYSTEM = "IsSystem";
    public static final String DATABASE_ROW_INTENT = "Intent";

    private static LauncherDatabase sInstance;

    public abstract ApplicationInfoDao applicationInfoDao();

    public static LauncherDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (LauncherDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(
                            context,
                            LauncherDatabase.class,
                            DATABASE_NAME
                    ).addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);

                            LauncherExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    LauncherDatabase.getInstance(context).firstLoad(context);
                                }
                            });
                        }

                        @Override
                        public void onOpen(@NonNull SupportSQLiteDatabase db) {
                            LauncherExecutors.getInstance().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    LauncherDatabase.getInstance(context)
                                            .updateDatabase(context);
                                }
                            });
                            Log.d("Launcher", "OK");

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
        final List<ApplicationInfoEntity> appsFromSystem =
                InstalledApplicationsParser.getInstalled(context);
        final List<ApplicationInfoEntity> appsFromDatabase = applicationInfoDao().loadAll();

        final List<ApplicationInfoEntity> uninstalledAppList = getUninstalledAppList(
                appsFromSystem,
                appsFromDatabase
        );

        applicationInfoDao().delete(uninstalledAppList);

        final List<ApplicationInfoEntity> installedAppList = getInstalledAppList(
                appsFromSystem,
                appsFromDatabase
        );

        applicationInfoDao().insert(installedAppList);

    }

    private static List<ApplicationInfoEntity> getUninstalledAppList(
            final List<ApplicationInfoEntity> appsFromSystem,
            final List<ApplicationInfoEntity> appsFromDatabase) {

        final List<ApplicationInfoEntity> uninstalledIntentList = new ArrayList<>(appsFromDatabase);

        for (ApplicationInfoEntity app : appsFromDatabase) {
            boolean installed = false;
            for (int i = 0; i < appsFromSystem.size(); i++) {
                if (app.getPackageName().equals(appsFromSystem.get(i).getPackageName())) {
                    installed = true;
                    break;
                }
            }

            if (installed) {
                uninstalledIntentList.remove(app);
            }
        }

        return uninstalledIntentList;
    }

    private static List<ApplicationInfoEntity> getInstalledAppList(
            final List<ApplicationInfoEntity> appsFromSystem,
            final List<ApplicationInfoEntity> appsFromDatabase) {

        final List<ApplicationInfoEntity> installedAppList = new ArrayList<>(appsFromSystem);

        for (ApplicationInfoEntity app : appsFromDatabase) {
            boolean deleted = false;
            final Intent databseAppIntent = app.getIntent();
            for (int i = 0; i < appsFromSystem.size(); i++) {
                final Intent systemAppIntent = appsFromSystem.get(i).getIntent();
                if (!databseAppIntent.equals(systemAppIntent)) {
                    deleted = true;
                    break;
                }
            }
            if (deleted) {
                installedAppList.remove(app);
            }
        }

        return installedAppList;
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

            applicationInfoDao().insert(new ApplicationInfoEntity(
                    appInfo.activityInfo.packageName,
                    installTime,
                    launchAmount,
                    systemApp,
                    appIntent
            ));
        }
    }

}
