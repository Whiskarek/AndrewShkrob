package whiskarek.andrewshkrob.database.converter;

import android.arch.persistence.room.TypeConverter;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import org.jetbrains.annotations.Contract;

import java.net.URISyntaxException;

public class IntentConverter {

    @TypeConverter
    @Nullable
    public static Intent toIntent(final String intent) {
        if (intent.equals("")) {
            return null;
        }

        try {
            return Intent.parseUri(intent, 0);
        } catch (URISyntaxException e) {
            Log.d("Launcher", e.toString());
        }

        return null;
    }

    @Contract("null -> !null")
    @TypeConverter
    public static String toString(final Intent intent) {
        if (intent == null) {
            return "";
        }

        return intent.toUri(0);
    }

}
