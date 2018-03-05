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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import whiskarek.andrewshkrob.LauncherApplication;
import whiskarek.andrewshkrob.LauncherExecutors;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.Sort;
import whiskarek.andrewshkrob.database.entity.ApplicationEntity;
import whiskarek.andrewshkrob.database.entity.DesktopCellEntity;

public class ApplicationViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<ApplicationEntity>> mObservableAppInfoList;
    private final MediatorLiveData<List<DesktopCellEntity>> mObservableDesktopCellList;
    private final MutableLiveData<Integer> mSortType;
    private final MutableLiveData<Boolean> mSolidModel;

    public ApplicationViewModel(final Application application) {
        super(application);

        mObservableAppInfoList = new MediatorLiveData<>();
        mObservableAppInfoList.setValue(null);

        mObservableDesktopCellList = new MediatorLiveData<>();
        mObservableDesktopCellList.setValue(null);

        mSortType = new MutableLiveData<>();
        initSortType(application.getApplicationContext());

        mSolidModel = new MutableLiveData<>();
        initSolidModel(application.getApplicationContext());

        final LiveData<List<ApplicationEntity>> appListLiveData = ((LauncherApplication) application)
                .getDatabase()
                .applicationDao()
                .loadAllApplications();

        mObservableAppInfoList.addSource(appListLiveData, new Observer<List<ApplicationEntity>>() {
            @Override
            public void onChanged(@Nullable final List<ApplicationEntity> applicationInfoEntities) {
                LauncherExecutors.getInstance().databaseIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        Sort.sort(applicationInfoEntities, mSortType.getValue());
                        mObservableAppInfoList.postValue(applicationInfoEntities);
                    }
                });
            }
        });

        mObservableAppInfoList.addSource(mSortType, new Observer<Integer>() {
            @Override
            public void onChanged(final @Nullable Integer sortType) {
                if (mObservableAppInfoList.getValue() != null) {
                    final List<ApplicationEntity> appInfoList =
                            new ArrayList<>(mObservableAppInfoList.getValue());
                    if (appInfoList != null) {
                        Sort.sort(appInfoList, sortType);

                        mObservableAppInfoList.postValue(appInfoList);
                    }
                }
            }
        });

        final LiveData<List<DesktopCellEntity>> desktopCellListLiveData =
                ((LauncherApplication) application).getDatabase().desktopCellDao().loadAll();

        mObservableDesktopCellList.addSource(desktopCellListLiveData, new Observer<List<DesktopCellEntity>>() {
            @Override
            public void onChanged(@Nullable final List<DesktopCellEntity> desktopCellEntities) {
                LauncherExecutors.getInstance().databaseIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mObservableDesktopCellList.postValue(desktopCellEntities);
                    }
                });
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

    public List<DesktopCellEntity> getShortcutsForScreen(final int screen) {
        List<DesktopCellEntity> shortcuts = new ArrayList<>();

        if (mObservableDesktopCellList.getValue() == null) {
            return shortcuts;
        }

        for (DesktopCellEntity s : mObservableDesktopCellList.getValue()) {
            if (s.getScreen() == screen) {
                shortcuts.add(s);
            }
        }

        Collections.sort(shortcuts, new Comparator<DesktopCellEntity>() {
            @Override
            public int compare(DesktopCellEntity o1, DesktopCellEntity o2) {
                return (o1.getPosition() < o2.getPosition() ? -1 :
                        (o1.getPosition() == o2.getPosition() ? 0 : 1));
            }
        });

        return shortcuts;
    }

}
