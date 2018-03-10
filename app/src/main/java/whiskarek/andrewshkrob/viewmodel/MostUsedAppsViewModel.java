package whiskarek.andrewshkrob.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.List;

import whiskarek.andrewshkrob.LauncherApplication;
import whiskarek.andrewshkrob.database.entity.ApplicationEntity;

public class MostUsedAppsViewModel extends AndroidViewModel {

    private MediatorLiveData<List<ApplicationEntity>> mMostUsedApps;

    public MostUsedAppsViewModel(final Application application) {
        super(application);

        mMostUsedApps = new MediatorLiveData<>();
        mMostUsedApps.setValue(null);

        final LiveData<List<ApplicationEntity>> appsFromDatabase =
                ((LauncherApplication) application).getDatabase().applicationDao().loadMostUsed(5);

        mMostUsedApps.addSource(appsFromDatabase, new Observer<List<ApplicationEntity>>() {
            @Override
            public void onChanged(@Nullable final List<ApplicationEntity> applicationEntities) {
                mMostUsedApps.postValue(applicationEntities);
            }
        });

    }

    public MediatorLiveData<List<ApplicationEntity>> getMostUsedApps() {
        return mMostUsedApps;
    }

}
