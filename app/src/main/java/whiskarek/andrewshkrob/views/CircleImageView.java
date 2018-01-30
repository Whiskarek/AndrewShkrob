package whiskarek.andrewshkrob.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import whiskarek.andrewshkrob.providers.CircleViewOutlineProvider;

public class CircledImageView extends android.support.v7.widget.AppCompatImageView {

    /**
     * Default constructor.
     */
    public CircledImageView(Context context) {
        super(context);

        setOutlineProvider(new CircleViewOutlineProvider());
        setClipToOutline(true);
    }

    /**
     * Default constructor.
     */
    public CircledImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setOutlineProvider(new CircleViewOutlineProvider());
        setClipToOutline(true);
    }

    /**
     * Default constructor.
     */
    public CircledImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setOutlineProvider(new CircleViewOutlineProvider());
        setClipToOutline(true);
    }

    /**
     * This method is used to get the circled image.
     */
    public static Bitmap getCircledBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;

        if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
            float smallest = Math.min(bmp.getWidth(), bmp.getHeight());
            float factor = smallest / radius;
            sbmp = Bitmap.createScaledBitmap(bmp,
                    (int) (bmp.getWidth() / factor),
                    (int) (bmp.getHeight() / factor), false);
        } else {
            sbmp = bmp;
        }

        Bitmap output = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888);


        final String color = "#BAB399";
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radius, radius);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(Color.parseColor(color));

        Canvas canvas = new Canvas(output);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(radius / 2 + 0.7f, radius / 2 + 0.7f,
                radius / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);

        return output;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(Math.min(widthMeasureSpec, heightMeasureSpec),
                Math.min(widthMeasureSpec, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = Bitmap.createBitmap(b);

        int w = getWidth();
        int h = getHeight();

        Bitmap roundBitmap = getCircledBitmap(bitmap, Math.min(w, h));
        canvas.drawBitmap(roundBitmap, 0, 0, null);
    }

}
