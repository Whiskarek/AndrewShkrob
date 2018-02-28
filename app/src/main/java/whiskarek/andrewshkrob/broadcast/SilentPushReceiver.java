package whiskarek.andrewshkrob.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.yandex.metrica.push.YandexMetricaPush;

public class SilentPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (intent.getAction()
                .equals(context.getPackageName() + ".action.ymp.SILENT_PUSH_RECEIVE")) {
            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(context);
            sharedPreferences.edit()
                    .putString(
                            "silent_push_message",
                            intent.getStringExtra(YandexMetricaPush.EXTRA_PAYLOAD)
                    ).apply();
        }
    }
}
