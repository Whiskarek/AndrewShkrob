package whiskarek.andrewshkrob.database.converter;

import android.arch.persistence.room.TypeConverter;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.net.URISyntaxException;

public class IntentConverter {

    @TypeConverter
    @Nullable
    public static Intent toIntent(final String intent) {
        try {
            return Intent.parseUri(intent, 0);
        } catch (URISyntaxException e) {
            Log.d("Launcher", e.toString());
        }

        return null;
    }

    @TypeConverter
    public static String toString(final Intent intent) {
        return intent.toUri(0);
    }

}
