package whiskarek.andrewshkrob.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class SquareView extends View {

    public SquareView(final Context context) {
        super(context);
    }

    public SquareView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareView(final Context context, @Nullable final AttributeSet attrs,
                      final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquareView(final Context context, @Nullable final AttributeSet attrs,
                      final int defStyleAttr,
                      final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
