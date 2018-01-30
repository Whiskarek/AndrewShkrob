package whiskarek.andrewshkrob.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;

import whiskarek.andrewshkrob.providers.CircleViewOutlineProvider;

public class CircleView extends View {

    /**
     * Default constructor.
     */
    public CircleView(Context context) {
        super(context);

        setOutlineProvider(new CircleViewOutlineProvider());
        setClipToOutline(true);
    }

    /**
     * Default constructor.
     */
    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOutlineProvider(new CircleViewOutlineProvider());
        setClipToOutline(true);
    }

    /**
     * Default constructor.
     */
    public CircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setOutlineProvider(new CircleViewOutlineProvider());
        setClipToOutline(true);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(Math.min(widthMeasureSpec, heightMeasureSpec),
                Math.min(widthMeasureSpec, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        final Paint paint = new Paint();

        paint.setAlpha(((ColorDrawable) getBackground()).getColor());

        canvas.drawCircle(0, 0, this.getWidth() / 2, paint);
    }

}
