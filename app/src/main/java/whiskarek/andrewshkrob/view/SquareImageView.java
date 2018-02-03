package whiskarek.andrewshkrob.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SquareImageView extends ImageView {


    public SquareImageView(final Context context) {
        super(context);
    }

    public SquareImageView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(final Context context, @Nullable final AttributeSet attrs,
                           final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SquareImageView(final Context context, @Nullable final AttributeSet attrs,
                           final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}
