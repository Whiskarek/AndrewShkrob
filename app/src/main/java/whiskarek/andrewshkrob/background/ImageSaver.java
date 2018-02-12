package whiskarek.andrewshkrob.background;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageSaver {
    private static final Object mLock = new Object();
    private static final String DIRECTORY_NAME = "images";
    private static volatile ImageSaver sInstance;

    private ImageSaver() {
    }

    public static ImageSaver getInstance() {
        if (null == sInstance) {
            synchronized (mLock) {
                if (null == sInstance) {
                    sInstance = new ImageSaver();
                }
            }
        }

        return sInstance;
    }

    public void saveImage(final Context context, final Bitmap bitmap, final String fileName) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(createFile(context, fileName));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (Exception e) {
            Log.e("Image", e.toString());
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                Log.e("Image", e.toString());
            }
        }
    }

    @NonNull
    private File createFile(final Context context, final String fileName) {
        File directory = context.getDir(DIRECTORY_NAME, Context.MODE_PRIVATE);

        return new File(directory, fileName);
    }

    public Bitmap loadImage(final Context context, final String fileName) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(createFile(context, fileName));
            return BitmapFactory.decodeStream(inputStream);
        } catch (Exception e) {
            Log.e("Image", e.toString());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                Log.e("Image", e.toString());
            }
        }
        return null;
    }
}
