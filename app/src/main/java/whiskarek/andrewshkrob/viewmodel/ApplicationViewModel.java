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
import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import whiskarek.andrewshkrob.LauncherApplication;
import whiskarek.andrewshkrob.LauncherExecutors;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.Sort;
import whiskarek.andrewshkrob.database.entity.ApplicationEntity;
import whiskarek.andrewshkrob.database.entity.ShortcutEntity;

public class ApplicationViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<ApplicationEntity>> mAppList;
    private final MutableLiveData<Integer> mSortType;
    private final MutableLiveData<Boolean> mSolidModel;

    public ApplicationViewModel(final Application application) {
        super(application);

        mAppList = new MediatorLiveData<>();
        mAppList.setValue(null);

        mSortType = new MutableLiveData<>();
        initSortType(application.getApplicationContext());

        mSolidModel = new MutableLiveData<>();
        initSolidModel(application.getApplicationContext());

        final LiveData<List<ApplicationEntity>> appListLiveData = ((LauncherApplication) application)
                .getDatabase()
                .applicationDao()
                .loadAllApplications();

        mAppList.addSource(appListLiveData, new Observer<List<ApplicationEntity>>() {
            @Override
            public void onChanged(@Nullable final List<ApplicationEntity> applicationInfoEntities) {
                LauncherExecutors.getInstance().databaseIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        Sort.sort(applicationInfoEntities, mSortType.getValue());
                        mAppList.postValue(applicationInfoEntities);
                    }
                });
            }
        });

        mAppList.addSource(mSortType, new Observer<Integer>() {
            @Override
            public void onChanged(final @Nullable Integer sortType) {
                if (mAppList.getValue() != null) {
                    final List<ApplicationEntity> appInfoList =
                            new ArrayList<>(mAppList.getValue());
                    if (appInfoList != null) {
                        Sort.sort(appInfoList, sortType);

                        mAppList.postValue(appInfoList);
                    }
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

    public LiveData<List<ApplicationEntity>> getApplications() {
        return mAppList;
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

    public Map<Integer, ApplicationEntity> getShortcuts(final List<ShortcutEntity> shortcutList) {
        final Map<Integer, ApplicationEntity> apps = new ArrayMap<>();

        final List<ApplicationEntity> appList = mAppList.getValue();

        if (appList == null) {
            return apps;
        }

        for (ShortcutEntity s : shortcutList) {
            ApplicationEntity app = null;
            for (ApplicationEntity a : appList) {
                if (s.getAppId() == a.getId()) {
                    app = a;
                    break;
                }
            }

            apps.put(s.getAppId(), app);
        }

        return apps;
    }
}
