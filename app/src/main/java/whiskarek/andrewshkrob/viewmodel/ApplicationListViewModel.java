package whiskarek.andrewshkrob.viewmodel;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.List;

import whiskarek.andrewshkrob.Application;
import whiskarek.andrewshkrob.LauncherApplication;
import whiskarek.andrewshkrob.database.ApplicationDatabase;
import whiskarek.andrewshkrob.database.converter.ApplicationConverter;
import whiskarek.andrewshkrob.database.entity.ApplicationEntity;

public class ApplicationListViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<Application>> mObservableApplications;

    public ApplicationListViewModel(final android.app.Application application) {
        super(application);
        mObservableApplications = new MediatorLiveData<>();
        mObservableApplications.setValue(null);
        final ApplicationDatabase database = ((LauncherApplication) application).getDatabase();
        mObservableApplications.addSource(database.applicationDao().loadAllApplications(),
                new Observer<List<ApplicationEntity>>() {

                    @Override
                    public void onChanged(@Nullable final List<ApplicationEntity> applicationEntities) {
                        if (database.getDatabaseCreated().getValue() != null) {
                            ((LauncherApplication) application).executors().diskIO().execute(new Runnable() {
                                @Override
                                public void run() {
                                    List<Application> applications = ApplicationConverter.getApplications(
                                            applicationEntities,
                                            application.getApplicationContext()
                                    );
                                    mObservableApplications.postValue(applications);
                                }
                            });
                        }
                    }
                });
    }

    public LiveData<List<Application>> getApplications() {
        return mObservableApplications;
    }

}
