package whiskarek.andrewshkrob;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Log;

import org.jetbrains.annotations.Contract;

public final class Settings {

    private static int mTheme = 1; // 1 - Light, 2 - Dark;
    private static int mModelType = Model.DEFAULT; // 1 - Default, 2 - Solid;
    private static boolean mAppWasLaunchedEarlier = false;

    /**
     * This method is used to load settings.
     */
    public static void loadSettings(Context context, Activity activity) {
        SharedPreferences sharedPreferences = activity
                .getSharedPreferences(context.getString(R.string.settings), Context.MODE_PRIVATE);

        mTheme = sharedPreferences.getInt("theme", 1);
        Log.d("Load", "Loaded mTheme: " + mTheme);
        mModelType = sharedPreferences.getInt("modelType", Model.DEFAULT);
        Log.d("Load", "Loaded mModelType: " + mModelType);
        mAppWasLaunchedEarlier = sharedPreferences.getBoolean("appWasLaunchedEarlier", false);
        Log.d("Load", "Loaded mAppWasLaunchedEarlier: " + Boolean.toString(mAppWasLaunchedEarlier));
    }

    /**
     * This method is used to save settings.
     */
    public static void saveSettings(Context context, Activity activity) {
        SharedPreferences sharedPreferences = activity
                .getSharedPreferences(context.getString(R.string.settings), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("theme", mTheme);
        Log.d("Save", "Saved mTheme: " + mTheme);
        editor.putInt("modelType", mModelType);
        Log.d("Save", "Saved mModelType: " + mModelType);
        editor.putBoolean("appWasLaunchedEarlier", mAppWasLaunchedEarlier);
        Log.d("Save", "Saved mAppWasLaunchedEarlier: " + Boolean.toString(mAppWasLaunchedEarlier));
        editor.apply();
    }

    @Contract(pure = true)
    public static int getModelType() {
        return mModelType;
    }

    public static void setModelType(int modelType) {
        Settings.mModelType = modelType;
    }

    @Contract(pure = true)
    public static int getTheme() {
        return mTheme;

    }

    public static void setTheme(int theme) {
        Settings.mTheme = theme;
    }

    @Contract(pure = true)
    public static boolean isAppWasLaunchedEarlier() {
        return mAppWasLaunchedEarlier;
    }

    public static void setAppWasLaunchedEarlier(boolean appWasLaunchedEarlier) {
        mAppWasLaunchedEarlier = appWasLaunchedEarlier;
    }

    public static final class Model {
        public static final int DEFAULT = 1;
        public static final int SOLID = 2;

        /**
         * This method is used to get number of rows in launcher activity.
         */
        public int getModel(int modelType, Resources res) {
            if (modelType == Model.DEFAULT) {
                return res.getInteger(R.integer.modelDefault);
            } else if (modelType == Model.SOLID) {
                return res.getInteger(R.integer.modelSolid);
            } else {
                return 0;
            }
        }

    }
}
