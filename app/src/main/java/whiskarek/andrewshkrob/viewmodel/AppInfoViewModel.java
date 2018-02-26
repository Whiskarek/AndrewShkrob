package whiskarek.andrewshkrob.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import java.util.List;

import whiskarek.andrewshkrob.AppInfo;

public class AppInfoViewModel extends AndroidViewModel{

    private final MediatorLiveData<List<AppInfo>> mObservableAppInfoList;

    public AppInfoViewModel(final Application application) {
        super(application);
        mObservableAppInfoList = new MediatorLiveData<>();
        mObservableAppInfoList.setValue(null);
        //mObservableAppInfoList.addSource();
    }

    public LiveData<List<AppInfo>> getApplications() {
        return mObservableAppInfoList;
    }


    /*public static class AppInfoViewModelFactory extends ViewModelProvider.Factory {

    }*/
}
