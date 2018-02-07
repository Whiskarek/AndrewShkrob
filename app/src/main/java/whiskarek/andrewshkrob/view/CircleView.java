package whiskarek.andrewshkrob.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import whiskarek.andrewshkrob.view.provider.CircleViewOutlineProvider;

public class CircleView extends View {

    /**
     * Default constructor.
     */
    public CircleView(final Context context) {
        super(context);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new CircleViewOutlineProvider());
            setClipToOutline(true);
        }
    }

    /**
     * Default constructor.
     */
    public CircleView(final Context context, final AttributeSet attrs) {
        super(context, attrs);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new CircleViewOutlineProvider());
            setClipToOutline(true);
        }
    }

    /**
     * Default constructor.
     */
    public CircleView(final Context context, final  AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setOutlineProvider(new CircleViewOutlineProvider());
            setClipToOutline(true);
        }
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    @Override
    protected void onDraw(final Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT);
        final Paint paint = new Paint();
        paint.setAlpha(((ColorDrawable) getBackground()).getColor());
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, paint);
    }
}
