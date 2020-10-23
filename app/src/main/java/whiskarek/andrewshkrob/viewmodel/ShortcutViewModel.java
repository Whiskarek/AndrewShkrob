package whiskarek.andrewshkrob.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.LauncherApplication;
import whiskarek.andrewshkrob.database.entity.ShortcutEntity;

public class ShortcutViewModel extends AndroidViewModel {

    private MediatorLiveData<List<ShortcutEntity>> mShortcutList;

    public ShortcutViewModel(final Application application) {
        super(application);

        mShortcutList = new MediatorLiveData<>();
        mShortcutList.postValue(null);

        final LiveData<List<ShortcutEntity>> shortcuts =
                ((LauncherApplication) application).getDatabase().desktopCellDao().loadAll();

        mShortcutList.addSource(shortcuts, new Observer<List<ShortcutEntity>>() {
            @Override
            public void onChanged(@Nullable final List<ShortcutEntity> shortcutEntities) {
                mShortcutList.postValue(shortcutEntities);
            }
        });
    }

    public MediatorLiveData<List<ShortcutEntity>> getShortcutList() {
        return mShortcutList;
    }

    public List<ShortcutEntity> getShortcutListForScreen(final int screen) {
        final List<ShortcutEntity> shortcutsForScreen = new ArrayList<>();

        if (mShortcutList == null || mShortcutList.getValue() == null) {
            return shortcutsForScreen;
        }
        final List<ShortcutEntity> shortcuts = mShortcutList.getValue();
        for (ShortcutEntity s : shortcuts) {
            if (s.getScreen() == screen) {
                shortcutsForScreen.add(s);
            }
        }

        return shortcutsForScreen;
    }

}
