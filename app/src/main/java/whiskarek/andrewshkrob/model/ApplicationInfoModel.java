package whiskarek.andrewshkrob.model;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public interface ApplicationInfoModel {

    String getPackageName();

    long getInstallTime();

    int getLaunchAmount();

    boolean isSystem();

    Intent getIntent();

    Drawable getIcon();

    String getLabel();

}
