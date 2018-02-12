package whiskarek.andrewshkrob.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

import java.util.List;

import whiskarek.andrewshkrob.Utils;

public class BackgroundViewModel extends AndroidViewModel {

    private final MediatorLiveData<List<Drawable>> mBackgroundImages;

    public BackgroundViewModel(final Application application) {
        super(application);
        mBackgroundImages = new MediatorLiveData<>();
        mBackgroundImages.setValue(null);
        mBackgroundImages.addSource(Utils.images, new Observer<List<Drawable>>() {
            @Override
            public void onChanged(@Nullable List<Drawable> drawables) {
                if (drawables != null) {
                    mBackgroundImages.postValue(drawables);
                }
            }
        });
    }

    public LiveData<List<Drawable>> getBackgroundImages() {
        return mBackgroundImages;
    }

}
