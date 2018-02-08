package whiskarek.andrewshkrob.view.provider;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

public class CircleViewOutlineProvider extends ViewOutlineProvider {

    @Override
    public void getOutline(final View view, final Outline outline) {
        outline.setOval(0, 0, view.getWidth(), view.getHeight());
    }
}
