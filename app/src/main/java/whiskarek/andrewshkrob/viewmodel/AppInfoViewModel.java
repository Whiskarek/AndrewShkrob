package whiskarek.andrewshkrob.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.AppInfo;
import whiskarek.andrewshkrob.LauncherApplication;
import whiskarek.andrewshkrob.LauncherExecutors;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.Sort;
import whiskarek.andrewshkrob.database.entity.ApplicationInfoEntity;

public class AppInfoViewModel extends AndroidViewModel {

    private static boolean mFirstLaunch = true;

    private final MediatorLiveData<List<AppInfo>> mObservableAppInfoList;
    private final MutableLiveData<Integer> mSortType;
    private final MutableLiveData<Boolean> mSolidModel;

    public AppInfoViewModel(final Application application) {
        super(application);
        mObservableAppInfoList = new MediatorLiveData<>();
        mObservableAppInfoList.setValue(new ArrayList<AppInfo>());
        mSortType = new MutableLiveData<>();
        mSolidModel = new MutableLiveData<>();
        initSortType(application.getApplicationContext());
        initSolidModel(application.getApplicationContext());

        final LiveData<List<ApplicationInfoEntity>> listLiveData = ((LauncherApplication) application)
                .getDatabase()
                .applicationInfoDao()
                .loadAllApplications();

        mObservableAppInfoList.addSource(listLiveData, new Observer<List<ApplicationInfoEntity>>() {
            @Override
            public void onChanged(@Nullable final List<ApplicationInfoEntity> applicationInfoEntities) {
                LauncherExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        //TODO Не подгружать весь список, а только добавленные приложения
                        mObservableAppInfoList.postValue(null);
                        final PackageManager packageManager = application.getPackageManager();
                        List<AppInfo> appList = new ArrayList<>();
                        for (ApplicationInfoEntity appEntity : applicationInfoEntities) {
                            final AppInfo app = AppInfo.Converter.getAppInfo(
                                    packageManager,
                                    appEntity
                            );
                            if (app != null) {
                                appList.add(app);
                                if (mFirstLaunch) {
                                    mObservableAppInfoList.postValue(appList);
                                }
                            }
                        }

                        if (mFirstLaunch) {
                            appList = new ArrayList<>(appList);
                        }
                        Sort.sort(appList, mSortType.getValue());
                        mObservableAppInfoList.postValue(appList);

                        applicationInfoEntities.clear();
                        mFirstLaunch = false;
                    }
                });
            }
        });

        mObservableAppInfoList.addSource(mSortType, new Observer<Integer>() {
            @Override
            public void onChanged(final @Nullable Integer sortType) {
                final List<AppInfo> appInfoList = mObservableAppInfoList.getValue();
                if (appInfoList != null) {
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
                        "3"));

        mSortType.setValue(sortType);
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
