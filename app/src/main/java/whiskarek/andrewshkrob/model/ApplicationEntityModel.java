package whiskarek.andrewshkrob.model;

public interface ApplicationEntityModel {

    String getPackageName();

    long getInstallTime();

    String getLaunchIntent();

    int getLaunchAmount();

    boolean isSystemApp();

}
