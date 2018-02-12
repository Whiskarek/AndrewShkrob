package whiskarek.andrewshkrob.background;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.LauncherApplication;
import whiskarek.andrewshkrob.R;
import whiskarek.andrewshkrob.Utils;

public class ImageService extends Service {

    public static final String ACTION_LOAD_IMAGE = "whiskarek.andrewshkrob.LOAD_IMAGE";
    private final ImageLoader mImageLoader;


    public ImageService() {
        mImageLoader = new ImageLoader();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(final Intent intent,
                              final int flags,
                              final int startId) {
        String action = intent.getAction();
        if (ACTION_LOAD_IMAGE.equals(action)) {
            load(startId);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void load(final int startId) {
        ((LauncherApplication) getApplication()).executors().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                final SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(ImageService.this);

                final boolean uniquePhotos = sharedPreferences.getBoolean(
                        getString(R.string.pref_key_unique_background_photos),
                        false);

                final List<String> imageUrls = mImageLoader.getImageUrl(uniquePhotos ? 2 : 1);
                final List<Drawable> images = new ArrayList<>();
                for (String imageUrl : imageUrls) {
                    if (!TextUtils.isEmpty(imageUrl)) {
                        Bitmap bitmap = mImageLoader.loadBitmap(imageUrl);
                        bitmap = prepareBitmap(bitmap);
                        images.add(new BitmapDrawable(getResources(), bitmap));
                    }
                }
                Utils.images.postValue(null);
                Utils.images.postValue(images);
            }
        });

        stopSelf(startId);
    }

    @Contract(pure = true)
    private Bitmap prepareBitmap(Bitmap bitmap) {

        final Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();
        final Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(point);
        } else {
            display.getSize(point);
        }
        if (point.y > bitmap.getHeight()) {
            final int delta = point.y - bitmap.getHeight();
            bitmap = resizeBitmap(bitmap, bitmap.getWidth() + delta, bitmap.getHeight() + delta);
        } else {
            final int delta = bitmap.getHeight() - point.y;
            bitmap = resizeBitmap(bitmap, bitmap.getWidth() - delta, bitmap.getHeight() - delta);
        }

        final int x = bitmap.getWidth() / 2 - point.x / 2;
        bitmap = Bitmap.createBitmap(bitmap, x, 0, point.x, point.y);

        return bitmap;
    }

    private Bitmap resizeBitmap(final Bitmap bitmap, final int newWidth, final int newHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bitmap, 0, 0, width, height, matrix, false);
        bitmap.recycle();
        return resizedBitmap;
    }
}
