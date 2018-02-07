package whiskarek.andrewshkrob.activity.main.fragments;

import android.support.v4.app.Fragment;

import java.util.List;

import whiskarek.andrewshkrob.activity.main.Application;

public abstract class LauncherFragment extends Fragment{

    public abstract void setData(final List<Application> data);

}
