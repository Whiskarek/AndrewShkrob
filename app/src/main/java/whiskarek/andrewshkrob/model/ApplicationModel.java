package whiskarek.andrewshkrob.model;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public interface ApplicationModel {

    String getPackageName();

    long getInstallTime();

    int getLaunchAmount();

    boolean isSystem();

    Intent getIntent();

    Drawable getIcon();

    String getLabel();

}
