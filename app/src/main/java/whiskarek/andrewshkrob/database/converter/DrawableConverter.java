package whiskarek.andrewshkrob.database.converter;

import android.arch.persistence.room.TypeConverter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DrawableConverter {

    private static String mAppDataDirectory;
    private static Resources mResources;
    public static final String ICON_FOLDER_NAME = "icons";
    private static final int QUALITY = 100;
    private static final Bitmap.CompressFormat FORMAT = Bitmap.CompressFormat.PNG;

    public DrawableConverter(final Resources resources, final String appDataDirectory) {
        mAppDataDirectory = appDataDirectory;
        mResources = resources;
    }

    @TypeConverter
    public static Drawable getDrawable(final String file) {
        final Bitmap icon = loadBitmapFromFile(file);

        return new BitmapDrawable(mResources, icon);
    }

    @NonNull
    @TypeConverter
    public static String getStringPath(final Drawable drawable) {
        final Bitmap bitmap = drawableToBitmap(drawable);
        final File path =
                new File(mAppDataDirectory + File.separator + ICON_FOLDER_NAME);

        if (!path.exists()) {
            path.mkdirs();
        }

        final String fileName = createFileName(path);

        saveBitmapToFile(path, fileName, bitmap);

        return fileName;
    }

    private static Bitmap drawableToBitmap(final Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            final BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;
        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        final Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private static void saveBitmapToFile(final File dir, final String fileName,
                                         final Bitmap bm) {

        File imageFile = new File(dir, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(imageFile);

            bm.compress(FORMAT, QUALITY, fos);

            fos.close();
        } catch (IOException e) {
            Log.e("Launcher", e.getMessage());
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    Log.e("Launcher", e1.getMessage());
                }
            }
        }
    }

    @Nullable
    private static Bitmap loadBitmapFromFile(final String file) {
        return BitmapFactory.decodeFile(
                mAppDataDirectory + "/" +
                        ICON_FOLDER_NAME + "/" +
                        file
        );
    }

    @NonNull
    private static String createFileName(final File path) {
        File[] files = path.listFiles();

        int lastNum = 0;

        if (files.length > 0) {
            for (File f : files) {
                final int last = Integer.parseInt(f.getName().split(".png")[0]);
                if (last > lastNum) {
                    lastNum = last;
                }
            }
        }

        return "" + (lastNum + 1) + ".png";
    }
}
