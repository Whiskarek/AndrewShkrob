package whiskarek.andrewshkrob.model;

import android.content.Intent;

public interface ApplicationInfoModel {

    String getPackageName();

    long getInstallTime();

    int getLaunchAmount();

    boolean isSystemApp();

    Intent getIntent();

}
