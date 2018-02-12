package whiskarek.andrewshkrob.database.converter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.jetbrains.annotations.Contract;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.Application;
import whiskarek.andrewshkrob.Utils;
import whiskarek.andrewshkrob.database.entity.ApplicationEntity;

public class ApplicationConverter {

    @Contract("null, _ -> null")
    public static List<Application> getApplications(
            final List<ApplicationEntity> applicationEntities,
            final Context context) {
        if (applicationEntities == null) {
            return null;
        }

        final List<Application> applications = new ArrayList<>();

        for (ApplicationEntity applicationEntity : applicationEntities) {
            final Application application = getApplication(applicationEntity, context);
            if (application != null) {
                applications.add(application);
            }
        }
        return applications;
    }

    @Nullable
    public static Application getApplication(final ApplicationEntity applicationEntity, final Context context) {
        try {
            final String packageName = applicationEntity.getPackageName();
            final long installTime = applicationEntity.getInstallTime();
            final int launchAmount = applicationEntity.getLaunchAmount();
            final boolean systemApp = applicationEntity.isSystemApp();
            final Intent launchIntent = Intent.parseUri(applicationEntity.getLaunchIntent(), 0);
            final Drawable appIcon = Utils.getAppIcon(context, packageName);
            final String appName = Utils.getAppName(context, packageName);

            Application application = new Application(
                    packageName,
                    appName,
                    appIcon,
                    launchIntent,
                    installTime,
                    launchAmount,
                    systemApp
            );
            return application;
        } catch (URISyntaxException uriSyntaxException) {
            Log.e("ApplicationEntityModel", uriSyntaxException.toString());
        }
        return null;
    }

    @Contract("null -> null")
    public static List<ApplicationEntity> getApplicationEntities
            (final List<Application> applications) {
        if (applications == null) {
            return null;
        }
        final List<ApplicationEntity> applicationEntities = new ArrayList<>();

        for (Application application : applications) {

            applicationEntities.add(getApplicationEntity(application));
        }
        return applicationEntities;
    }

    @NonNull
    public static ApplicationEntity getApplicationEntity(final Application application) {
        return new ApplicationEntity(
                application.getPackageName(),
                application.getInstallTime(),
                application.getLaunchAmount(),
                application.getLaunchIntent().toUri(0),
                application.isSystemApp()
        );
    }


}
