package whiskarek.andrewshkrob.database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import com.yandex.metrica.YandexMetrica;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.Application;
import whiskarek.andrewshkrob.LauncherExecutors;
import whiskarek.andrewshkrob.Utils;
import whiskarek.andrewshkrob.database.converter.ApplicationConverter;
import whiskarek.andrewshkrob.database.dao.ApplicationDao;
import whiskarek.andrewshkrob.database.entity.ApplicationEntity;

@Database(entities = {ApplicationEntity.class}, version = 1)
public abstract class ApplicationDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "Applications";
    private static ApplicationDatabase sInstance;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static ApplicationDatabase getInstance(final Context context,
                                                  final LauncherExecutors launcherExecutors) {
        YandexMetrica.reportEvent("Database", "Instance to database");
        if (sInstance == null) {
            synchronized (ApplicationDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room
                            .databaseBuilder(context, ApplicationDatabase.class, DATABASE_NAME)
                            .addCallback(new Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    launcherExecutors.diskIO().execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (sInstance.applicationDao().count() == 0) {
                                                final List<Application> applications =
                                                        Utils.loadApplications(context);

                                                final List<ApplicationEntity> applicationEntities =
                                                        ApplicationConverter.getApplicationEntities(applications);

                                                final ApplicationDatabase applicationDatabase =
                                                        ApplicationDatabase.getInstance(context, launcherExecutors);

                                                YandexMetrica.reportEvent("Database", "Insert data to database");
                                                insertData(applicationDatabase, applicationEntities);
                                            }
                                        }
                                    });
                                }
                            })
                            .build();
                    sInstance.updateDatabaseCreated(context);
                    sInstance.update(context, launcherExecutors);
                }
            }
        }

        return sInstance;
    }

    private static synchronized void insertData(final ApplicationDatabase database,
                                                final List<ApplicationEntity> applications) {
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                database.applicationDao().insertAll(applications);
            }
        });
    }

    private static synchronized void deleteData(final ApplicationDatabase database,
                                                final List<ApplicationEntity> applications) {
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                database.applicationDao().delete(applications);
            }
        });
    }

    public abstract ApplicationDao applicationDao();

    private void update(final Context context, final LauncherExecutors launcherExecutors) {
        launcherExecutors.diskIO().execute(new Runnable() {
            private List<ApplicationEntity>
            getAppsToAdd(final List<Application> applicationsInstalledInSystem,
                         final List<ApplicationEntity> applicationsFromDatabase) {
                final List<ApplicationEntity> appsToAdd = new ArrayList<>();
                for (int i = 0; i < applicationsInstalledInSystem.size(); i++) {
                    boolean exists = false;
                    final String packageName = applicationsInstalledInSystem.get(i)
                            .getPackageName();
                    for (int j = 0; j < applicationsFromDatabase.size(); j++) {
                        if (packageName.equals(applicationsFromDatabase.get(j).getPackageName())) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        appsToAdd.add(ApplicationConverter
                                .getApplicationEntity(applicationsInstalledInSystem.get(i)));
                    }
                }
                return appsToAdd;
            }

            private List<ApplicationEntity>
            getAppsToRemove(final List<Application> applicationsInstalledInSystem,
                            final List<ApplicationEntity> applicationsFromDatabase) {
                final List<ApplicationEntity> appsToRemove = new ArrayList<>();
                for (int i = 0; i < applicationsFromDatabase.size(); i++) {
                    boolean exists = false;
                    final String packageName = applicationsFromDatabase.get(i).getPackageName();
                    for (int j = 0; j < applicationsInstalledInSystem.size(); j++) {
                        if (packageName.equals(applicationsInstalledInSystem
                                .get(j).getPackageName())) {
                            exists = true;
                            break;
                        }
                    }
                    if (!exists) {
                        appsToRemove.add(applicationsFromDatabase.get(i));
                    }
                }
                return appsToRemove;
            }

            @Override
            public void run() {
                final List<Application> applicationsInstalledInSystem =
                        Utils.loadApplications(context);

                if (applicationDao().count() != 0) {
                    final List<ApplicationEntity> applicationsFromDatabase =
                            applicationDao().loadAll();

                    final List<ApplicationEntity> appsToAdd = getAppsToAdd(
                            applicationsInstalledInSystem,
                            applicationsFromDatabase
                    );
                    final List<ApplicationEntity> appsToRemove = getAppsToRemove(
                            applicationsInstalledInSystem,
                            applicationsFromDatabase
                    );

                    if (appsToAdd.size() != 0) {
                        YandexMetrica.reportEvent("Database", "Insert data to database");
                        insertData(sInstance, appsToAdd);
                    }
                    if (appsToRemove.size() != 0) {
                        YandexMetrica.reportEvent("Database", "Remove data from database");
                        deleteData(sInstance, appsToRemove);
                    }
                }
            }
        });
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
