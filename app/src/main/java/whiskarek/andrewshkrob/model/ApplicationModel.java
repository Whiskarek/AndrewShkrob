package whiskarek.andrewshkrob.model;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

public interface ApplicationModel {

    String getAppName();

    String getPackageName();

    @Nullable
    Drawable getAppIcon();

    Intent getLaunchIntent();

    int getLaunchAmount();

    long getInstallTime();

    boolean isSystemApp();

}
