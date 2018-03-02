package whiskarek.andrewshkrob.database.converter;

import android.arch.persistence.room.TypeConverter;
import android.content.Intent;
import android.util.Log;

import java.net.URISyntaxException;

public class IntentConverter {

    @TypeConverter
    public static Intent toIntent(String intent) {
        try {
            return Intent.parseUri(intent, 0);
        } catch (URISyntaxException e) {
            Log.d("Launcher", e.toString());
        }

        return null;
    }

    @TypeConverter
    public static String toString(Intent intent) {
        return intent.toUri(0);
    }
}
