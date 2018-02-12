package whiskarek.andrewshkrob.activity.main;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.yandex.metrica.YandexMetrica;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import io.fabric.sdk.android.Fabric;

public class MainActivityObserver implements LifecycleObserver {

    private final Lifecycle mLifecycle;
    private final Context mContext;

    public MainActivityObserver(final Lifecycle lifecycle, final Context context) {
        mLifecycle = lifecycle;
        mContext = context;
        mLifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        Fabric.with(mContext, new Crashlytics());

        YandexMetrica.reportEvent("MainActivity", "onCreate");

        checkForUpdates();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResume() {
        YandexMetrica.reportEvent("MainActivity", "onResume");

        checkForCrashes();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        YandexMetrica.reportEvent("MainActivity", "onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onStart() {
        YandexMetrica.reportEvent("MainActivity", "onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onStop() {
        YandexMetrica.reportEvent("MainActivity", "onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void onDestroy() {
        YandexMetrica.reportEvent("MainActivity", "onDestroy");
        unregisterManagers();
        mLifecycle.removeObserver(this);
    }

    private void checkForCrashes() {
        CrashManager.register(mContext);
    }

    private void checkForUpdates() {
        // Remove this for store builds!
        UpdateManager.register((MainActivity) mContext);
    }

    private void unregisterManagers() {
        UpdateManager.unregister();
    }

}
