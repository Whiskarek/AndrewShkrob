package whiskarek.andrewshkrob.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import whiskarek.andrewshkrob.activity.main.MainActivity;


public class ApplicationChangesReceiver extends BroadcastReceiver {

    private final MainActivity mActivity;

    public ApplicationChangesReceiver(MainActivity activity) {
        mActivity = activity;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.i("BROADCAST", "Broadcast: " + intent.toString());

        switch (intent.getAction()) {
            case Intent.ACTION_PACKAGE_REMOVED: {
                mActivity.deleteApp(intent);
                break;
            }
            case Intent.ACTION_PACKAGE_ADDED: {
                mActivity.addApp(intent);
                break;
            }
        }

    }
}
