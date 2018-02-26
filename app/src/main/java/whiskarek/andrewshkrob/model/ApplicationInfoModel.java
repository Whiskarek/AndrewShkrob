package whiskarek.andrewshkrob.model;

public interface ApplicationInfoModel {

    String getPackageName();

    long getInstallTime();

    int getLaunchAmount();

    boolean isSystemApp();

}
