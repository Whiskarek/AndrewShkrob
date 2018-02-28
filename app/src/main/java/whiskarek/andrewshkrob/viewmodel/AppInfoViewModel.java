package whiskarek.andrewshkrob.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import whiskarek.andrewshkrob.AppInfo;
import whiskarek.andrewshkrob.LauncherApplication;
import whiskarek.andrewshkrob.LauncherExecutors;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.Sort;
import whiskarek.andrewshkrob.database.entity.ApplicationInfoEntity;

public class AppInfoViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<AppInfo>> mObservableAppInfoList;
    private final MutableLiveData<Integer> mSortType;
    private final MutableLiveData<Boolean> mSolidModel;

    public AppInfoViewModel(final Application application) {
        super(application);
        mObservableAppInfoList = new MediatorLiveData<>();
        mObservableAppInfoList.setValue(null);
        mSortType = new MutableLiveData<>();
        mSolidModel = new MutableLiveData<>();
        initSortType(application.getApplicationContext());
        initSolidModel(application.getApplicationContext());

        LiveData<List<ApplicationInfoEntity>> listLiveData = ((LauncherApplication) application)
                .getDatabase()
                .applicationInfoDao()
                .loadAllApplications();

        mObservableAppInfoList.addSource(listLiveData, new Observer<List<ApplicationInfoEntity>>() {
            @Override
            public void onChanged(@Nullable final List<ApplicationInfoEntity> applicationInfoEntities) {
                LauncherExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Launcher", "Start monitoring");
                        long start = System.currentTimeMillis();
                        final List<AppInfo> appInfoList = AppInfo.Converter.getAppInfoList(
                                applicationInfoEntities,
                                application.getApplicationContext()
                        );
                        Log.d("Launcher", "converting: " + (System.currentTimeMillis() - start));
                        start = System.currentTimeMillis();
                        Sort.sort(appInfoList, mSortType.getValue());
                        Log.d("Launcher", "sorting: " + (System.currentTimeMillis() - start));

                        mObservableAppInfoList.postValue(appInfoList);
                    }
                }
            );
            }
        });

        mObservableAppInfoList.addSource(mSortType, new Observer<Integer>() {
            @Override
            public void onChanged(final @Nullable Integer sortType) {
                final List<AppInfo> appInfoList = mObservableAppInfoList.getValue();
                if(appInfoList != null) {
                    Sort.sort(appInfoList, sortType);

                    mObservableAppInfoList.postValue(appInfoList);
                }
            }
        });

    }

    private void initSortType(final Context context) {

        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        final int sortType = Integer.parseInt(sharedPreferences
                .getString(context.getResources().getString(R.string.pref_key_sort_type),
                        "0"));

        mSortType.postValue(sortType);
    }

    private void initSolidModel(final Context context) {
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);

        final boolean solidModel = sharedPreferences
                .getBoolean(context.getResources().getString(R.string.pref_key_model_solid),
                        false);

        mSolidModel.setValue(solidModel);
    }

    public LiveData<List<AppInfo>> getApplications() {
        return mObservableAppInfoList;
    }

    public LiveData<Boolean> getModelType() {
        return mSolidModel;
    }

    public void setSortType(final int sortType) {
        mSortType.postValue(sortType);
    }

    public void setSolidModel(final boolean solid) {
        mSolidModel.setValue(solid);
    }

}
