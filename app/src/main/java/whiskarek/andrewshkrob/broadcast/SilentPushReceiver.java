package whiskarek.andrewshkrob.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yandex.metrica.push.YandexMetricaPush;

public class SilentPushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final String payload = intent.getStringExtra(YandexMetricaPush.EXTRA_PAYLOAD);
    }
}
