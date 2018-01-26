package whiskarek.andrewshkrob;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.widget.ImageView;

/**
 * Created by Andrew on 26.01.2018.
 */

@SuppressLint("AppCompatCustomView")
public class CircleImageView extends ImageView {

    public CircleImageView(Context context){
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas){
        final float halfWidth = canvas.getWidth() / 2;
        final float halfHeight = canvas.getHeight() / 2;
        final float radius = Math.max(halfWidth, halfHeight);
        final Path path = new Path();
        path.addCircle(halfWidth, halfHeight, radius, Path.Direction.CCW);

        canvas.clipPath(path);

        super.onDraw(canvas);
    }

}
