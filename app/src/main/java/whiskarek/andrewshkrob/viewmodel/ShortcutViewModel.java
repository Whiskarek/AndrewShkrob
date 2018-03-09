package whiskarek.andrewshkrob.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;

import java.util.List;
import java.util.Map;

import whiskarek.andrewshkrob.LauncherApplication;
import whiskarek.andrewshkrob.LauncherExecutors;
import whiskarek.andrewshkrob.database.entity.ApplicationEntity;
import whiskarek.andrewshkrob.database.entity.ShortcutEntity;

public class ShortcutViewModel extends AndroidViewModel {

    private MediatorLiveData<Map<Integer, ApplicationEntity>> mShortcutList;

    public ShortcutViewModel(final Application application) {
        super(application);

        mShortcutList = new MediatorLiveData<>();
        mShortcutList.postValue(null);

        final LiveData<List<ShortcutEntity>> shortcuts =
                ((LauncherApplication) application).getDatabase().desktopCellDao().loadAll();

        mShortcutList.addSource(shortcuts, new Observer<List<ShortcutEntity>>() {
            @Override
            public void onChanged(@Nullable final List<ShortcutEntity> shortcutEntities) {
                LauncherExecutors.getInstance().databaseIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        final Map<Integer, ApplicationEntity> app = new ArrayMap<>();

                        for (ShortcutEntity shortcut : shortcutEntities) {
                            app.put(shortcut.getPosition(), ((LauncherApplication) application)
                                    .getDatabase()
                                    .applicationDao()
                                    .getAppWithId(shortcut.getAppId()));
                        }

                        mShortcutList.postValue(app);
                    }
                });
            }
        });

        final LiveData<List<ApplicationEntity>> apps =
                ((LauncherApplication) application).getDatabase()
                        .applicationDao().loadAllApplications();

        mShortcutList.addSource(apps, new Observer<List<ApplicationEntity>>() {
            @Override
            public void onChanged(@Nullable final List<ApplicationEntity> apps) {
                LauncherExecutors.getInstance().databaseIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        final Map<Integer, ApplicationEntity> app = new ArrayMap<>();

                        for (ShortcutEntity shortcut : shortcuts.getValue()) {
                            app.put(shortcut.getPosition(), ((LauncherApplication) application)
                                    .getDatabase()
                                    .applicationDao()
                                    .getAppWithId(shortcut.getAppId()));
                        }

                        mShortcutList.postValue(app);
                    }
                });
            }
        });
    }

    public MediatorLiveData<Map<Integer, ApplicationEntity>> getShortcutList() {
        return mShortcutList;
    }

}
