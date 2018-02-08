package whiskarek.andrewshkrob.activity.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import whiskarek.andrewshkrob.Application;
import whiskarek.andrewshkrob.Utils;

public class ApplicationDatabaseHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "Applications.db";

    public ApplicationDatabaseHelper(final Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {
        db.execSQL(Database.CREATE_TABLE_SCRIPT);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        db.execSQL(Database.DROP_TABLE_SCRIPT);
        onCreate(db);
    }

    @Override
    public void onDowngrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addToDatabase(final List<Application> apps) {
        try {
            final SQLiteDatabase db = this.getWritableDatabase();

            for (Application app : apps) {
                final ContentValues contentValues = new ContentValues();
                contentValues.put(Database.Columns.FIELD_APP_PACKAGE_NAME, app.getPackageName());
                contentValues.put(Database.Columns.FIELD_APP_LAUNCH_INTENT, app.getLaunchIntent().toUri(0));
                contentValues.put(Database.Columns.FIELD_APP_INSTALL_TIME, app.getInstallTime());
                contentValues.put(Database.Columns.FIELD_APP_LAUNCH_AMOUNT, app.getLaunchAmount());
                contentValues.put(Database.Columns.FIELD_APP_IS_SYSTEM, app.isSystemApp() ? 1 : 0);

                db.insert(Database.TABLE_NAME, null, contentValues);
            }

        } catch (SQLException e) {
            Log.e("SQLite", e.toString());
        }
    }

    public List<Application> getFromDatabase(final Context context) {
        final List<Application> apps = new ArrayList<>();

        try {
            final String[] columns = {
                    Database.Columns.FIELD_APP_PACKAGE_NAME,
                    Database.Columns.FIELD_APP_LAUNCH_INTENT,
                    Database.Columns.FIELD_APP_INSTALL_TIME,
                    Database.Columns.FIELD_APP_LAUNCH_AMOUNT,
                    Database.Columns.FIELD_APP_IS_SYSTEM
            };

            final SQLiteDatabase db = getReadableDatabase();
            final Cursor cursor = db.query(
                    Database.TABLE_NAME,
                    columns,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                final String packageName = cursor
                        .getString(cursor.getColumnIndex(Database.Columns.FIELD_APP_PACKAGE_NAME));
                final Intent launchIntent = Intent.parseUri(cursor.getString(
                        cursor.getColumnIndex(Database.Columns.FIELD_APP_LAUNCH_INTENT)), 0);
                final long installTime = cursor.getLong(
                        cursor.getColumnIndex(Database.Columns.FIELD_APP_INSTALL_TIME));
                final int launchAmount = cursor.getInt(
                        cursor.getColumnIndex(Database.Columns.FIELD_APP_LAUNCH_AMOUNT));
                final boolean systemApp = cursor.getInt(
                        cursor.getColumnIndex(Database.Columns.FIELD_APP_IS_SYSTEM)) == 1;

                final Drawable appIcon = Utils.getAppIcon(context, packageName);
                final String appName = Utils.getAppName(context, packageName);

                apps.add(new Application(
                        packageName,
                        appName,
                        appIcon,
                        launchIntent,
                        installTime,
                        launchAmount,
                        systemApp
                ));
            }
            cursor.close();
        } catch (SQLException e) {
            Log.e("SQLite", e.toString());
        } catch (URISyntaxException e) {
            Log.e("SQLite", e.toString());
        }
        return apps;
    }

    public boolean isEmpty() {
        try {
            final SQLiteDatabase db = this.getReadableDatabase();
            final Cursor cursor = db.rawQuery("SELECT * FROM " + Database.TABLE_NAME + ";",
                    null);

            if (cursor.getCount() == 0)
                return true;
        } catch (SQLException e) {
            Log.e("SQLite", e.toString());
        }
        return false;
    }

    public void clearData() {
        try {
            SQLiteDatabase db = getWritableDatabase();
            db.delete(Database.TABLE_NAME, null, null);
            db.close();
        } catch (SQLiteException e) {
            Log.e("SQLite", e.toString());
        }
    }
}