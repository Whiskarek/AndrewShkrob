package whiskarek.andrewshkrob.background;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.util.List;
import java.util.Timer;

import whiskarek.andrewshkrob.InstalledApplicationsParser;
import whiskarek.andrewshkrob.LauncherExecutors;
import whiskarek.andrewshkrob.database.LauncherDatabase;
import whiskarek.andrewshkrob.database.converter.DrawableConverter;
import whiskarek.andrewshkrob.database.entity.ApplicationEntity;

public class ApplicationManager extends Service {

    private static final long UPDATE_TIME = 10000;
    private static final Timer mUpdateTimer = new Timer();

    private final BroadcastReceiver mApplicationListener = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            if (intent == null) {
                return;
            }

            switch (intent.getAction()) {
                case Intent.ACTION_PACKAGE_ADDED: {
                    Log.d("Launcher", "Package Installed");
                    LauncherExecutors.getInstance().databaseIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            final PackageManager packageManager = ApplicationManager
                                    .this
                                    .getPackageManager();
                            final String packageName = intent.getData().getSchemeSpecificPart();
                            final Intent appIntent = new Intent(Intent.ACTION_MAIN, null);
                            appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                            appIntent.setPackage(packageName);

                            final List<ResolveInfo> apps =
                                    packageManager.queryIntentActivities(appIntent, 0);

                            for (ResolveInfo app : apps) {
                                ApplicationEntity applicationEntity = InstalledApplicationsParser
                                        .getApplicationInfoEntity(packageManager, app);
                                LauncherDatabase
                                        .getInstance(ApplicationManager.this)
                                        .applicationInfoDao()
                                        .insert(applicationEntity);
                            }
                        }
                    });

                    break;
                }
                case Intent.ACTION_PACKAGE_REMOVED: {
                    Log.d("Launcher", "Package Removed");
                    LauncherExecutors.getInstance().databaseIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            final String packageName = intent.getData().getSchemeSpecificPart();
                            final String iconPath = LauncherDatabase
                                    .getInstance(ApplicationManager.this)
                                    .applicationInfoDao()
                                    .getIconPath(packageName);

                            LauncherDatabase
                                    .getInstance(ApplicationManager.this)
                                    .applicationInfoDao()
                                    .delete(packageName);

                            new File(context.getFilesDir().toString() +
                                    File.separator + DrawableConverter.ICON_FOLDER_NAME +
                                    File.separator + iconPath
                            ).delete();
                        }
                    });

                    break;
                }
            }
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d("Launcher", "Service.onCreate()");

        final IntentFilter applicationChangesFilter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        applicationChangesFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        applicationChangesFilter.addDataScheme("package");

        registerReceiver(mApplicationListener, applicationChangesFilter);
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mApplicationListener);

        super.onDestroy();
    }

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {

        Log.d("Launcher", "Service.onStartCommand()");

        return super.onStartCommand(intent, flags, startId);
    }
}