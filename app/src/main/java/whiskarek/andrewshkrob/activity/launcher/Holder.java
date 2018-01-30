package whiskarek.andrewshkrob.activity.launcher;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import whiskarek.andrewshkrob.R;

public class Holder {

    static class GridHolder extends RecyclerView.ViewHolder {
        private final View mLauncherImage;
        private final TextView mLauncherText;

        GridHolder(final View view) {
            super(view);

            mLauncherImage = view.findViewById(R.id.launcher_image);
            mLauncherText = view.findViewById(R.id.launcher_text);
        }

        View getLauncherImage() {
            return mLauncherImage;
        }

        TextView getLauncherText() {
            return mLauncherText;
        }
    }

}
