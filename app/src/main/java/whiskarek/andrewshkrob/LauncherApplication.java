package whiskarek.andrewshkrob;

import android.app.Application;
import android.os.StrictMode;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.push.YandexMetricaPush;

import whiskarek.andrewshkrob.database.LauncherDatabase;

public class LauncherApplication extends Application {

    private static final String API_key = "03576e15-b9f8-4d10-8e98-158c246261cd";

    @Override
    public void onCreate() {
        super.onCreate();
        /*
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        */
        // Инициализация AppMetrica SDK
        YandexMetrica.activate(getApplicationContext(), API_key);
        // Отслеживание активности пользователей
        YandexMetrica.enableActivityAutoTracking(this);

        YandexMetricaPush.init(getApplicationContext());

        YandexMetrica.reportEvent("Application.onCreate()");
    }

    public LauncherDatabase getDatabase() {
        return LauncherDatabase.getInstance(this);
    }

}
