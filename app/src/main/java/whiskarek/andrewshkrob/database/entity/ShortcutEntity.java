package whiskarek.andrewshkrob.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;
import android.support.annotation.NonNull;

import whiskarek.andrewshkrob.database.ShortcutDatabase;
import whiskarek.andrewshkrob.model.entity.DesktopCellModel;

@Entity(tableName = ShortcutDatabase.NAME,
        indices = {@Index(value = ShortcutDatabase.ROW_ID, unique = true),
                   @Index(value = ShortcutDatabase.ROW_POSITION, unique = true)})
public class ShortcutEntity implements DesktopCellModel {

    public static final class ShortcutType {
        public static final int APP = 0;
        public static final int CONTACT = 1;
        public static final int URL = 2;
    }

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = ShortcutDatabase.ROW_ID)
    private int mAppId;

    @ColumnInfo(name = ShortcutDatabase.ROW_POSITION)
    private int mPosition;

    @ColumnInfo(name = ShortcutDatabase.ROW_SCREEN)
    private int mScreen;

    @ColumnInfo(name = ShortcutDatabase.ROW_CELL_TYPE)
    private int mShortcutType;

    @ColumnInfo(name = ShortcutDatabase.ROW_INTENT)
    private Intent mIntent;

    public ShortcutEntity(final int appId, final int position, final int screen,
                          final int shortcutType, final Intent intent) {
        mAppId = appId;
        mPosition = position;
        mScreen = screen;
        mShortcutType = shortcutType;
        mIntent = intent;
    }

    @Override
    public int getAppId() {
        return mAppId;
    }

    @Override
    public int getPosition() {
        return mPosition;
    }

    @Override
    public int getScreen() {
        return mScreen;
    }

    @Override
    public int getShortcutType() {
        return mShortcutType;
    }

    @Override
    public Intent getIntent() {
        return mIntent;
    }

}
