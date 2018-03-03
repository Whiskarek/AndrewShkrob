package whiskarek.andrewshkrob.background;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;

import whiskarek.andrewshkrob.LauncherExecutors;

public class ApplicationManager extends Service {

    private static final long UPDATE_TIME = 10000;
    private static final Timer mUpdateTimer = new Timer();

    private static final BroadcastReceiver mApplicationListener = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context context, final Intent intent) {
            if (intent == null) {
                return;
            }

            switch (intent.getAction()) {
                case Intent.ACTION_PACKAGE_ADDED: {

                    LauncherExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                    break;
                }
                case Intent.ACTION_PACKAGE_REMOVED: {

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