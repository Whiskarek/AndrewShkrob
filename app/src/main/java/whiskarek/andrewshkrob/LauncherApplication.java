package whiskarek.andrewshkrob;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ResolveInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;

import java.util.Timer;
import java.util.TimerTask;

import whiskarek.andrewshkrob.background.ImageService;
import whiskarek.andrewshkrob.database.ApplicationDatabase;
import whiskarek.andrewshkrob.database.converter.ApplicationConverter;
import whiskarek.andrewshkrob.database.entity.ApplicationEntity;

public class LauncherApplication extends Application {

    private static final String YANDEX_METRICA_API_KEY = "03576e15-b9f8-4d10-8e98-158c246261cd";
    private static Timer imageLoad;
    private LauncherExecutors mLauncherExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        mLauncherExecutors = new LauncherExecutors();
        setUpApplicationChangesReceiver();

        imageLoad = new Timer();
        updateTimer();
        initYandex();

    }

    private void setUpApplicationChangesReceiver() {
        final BroadcastReceiver mApplicationChangesListener = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                switch (intent.getAction()) {
                    case Intent.ACTION_PACKAGE_ADDED: {
                        YandexMetrica.reportEvent("BroadcastReceiver",
                                "ACTION_PACKAGE_ADDED");
                        mLauncherExecutors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                final String packageName = intent.getData().getSchemeSpecificPart();
                                final Intent data = new Intent();
                                data.setPackage(packageName);
                                data.addCategory(Intent.CATEGORY_LAUNCHER);
                                final ResolveInfo appInfo =
                                        getPackageManager().resolveActivity(data, 0);

                                whiskarek.andrewshkrob.Application app =
                                        Utils.getApp(appInfo, context);

                                ApplicationEntity appEntity =
                                        ApplicationConverter.getApplicationEntity(app);

                                getDatabase().applicationDao().insertApp(appEntity);
                            }
                        });
                        break;
                    }
                    case Intent.ACTION_PACKAGE_REMOVED: {
                        YandexMetrica.reportEvent("BroadcastReceiver",
                                "ACTION_PACKAGE_REMOVED");
                        mLauncherExecutors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                final String packageName = intent.getData().getSchemeSpecificPart();
                                getDatabase().applicationDao().delete(packageName);
                            }
                        });

                        break;
                    }
                }
            }
        };

        final IntentFilter applicationChangesFilter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        applicationChangesFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        applicationChangesFilter.addDataScheme("package");

        registerReceiver(mApplicationChangesListener, applicationChangesFilter);
    }

    private void initYandex() {
        YandexMetrica.activate(getApplicationContext(), YANDEX_METRICA_API_KEY);
        YandexMetrica.enableActivityAutoTracking(this);

        final YandexMetricaConfig.Builder configBuilder =
                YandexMetricaConfig.newConfigBuilder(YANDEX_METRICA_API_KEY);

        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);
        final boolean isFirstApplicationLaunch = sharedPreferences.getBoolean(
                getString(R.string.pref_key_first_application_launch),
                true
        );
        if (!isFirstApplicationLaunch) {
            configBuilder.handleFirstActivationAsUpdate(true);
        }

        YandexMetricaConfig extendedConfig = configBuilder.build();
        YandexMetrica.activate(getApplicationContext(), extendedConfig);
        YandexMetrica.enableActivityAutoTracking(this);

        sharedPreferences.edit()
                .putBoolean(getString(R.string.pref_key_first_application_launch), false).apply();
    }

    private int getDefaultUpdateTime() {
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this);

        final String key = getString(R.string.pref_key_background_change_frequency);
        final String defaultValue = getResources()
                .getStringArray(R.array.pref_background_change_frequency_entry_values)[0];

        final String time = sharedPreferences.getString(key, defaultValue);
        Log.d("TIME", time);
        return Integer.parseInt(time);
    }

    public ApplicationDatabase getDatabase() {
        return ApplicationDatabase.getInstance(this, mLauncherExecutors);
    }

    public LauncherExecutors executors() {
        return mLauncherExecutors;
    }

    public void updateTimer() {
        Log.d("TIMER", "Update");
        imageLoad.cancel();
        imageLoad = new Timer();
        final TimerTask mTimerTask = new TimerTask() {
            @Override
            public void run() {
                final Intent intent = new Intent(LauncherApplication.this,
                        ImageService.class);
                intent.setAction(ImageService.ACTION_LOAD_IMAGE);
                Log.e("TIMER", "Start Service");
                startService(intent);
            }
        };
        imageLoad.schedule(mTimerTask, 0L, getDefaultUpdateTime() * 60000);
    }
}
