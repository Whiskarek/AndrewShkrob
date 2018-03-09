package whiskarek.andrewshkrob.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.content.Intent;
import android.support.annotation.NonNull;

import whiskarek.andrewshkrob.database.DesktopCellDatabase;
import whiskarek.andrewshkrob.model.entity.DesktopCellModel;

@Entity(tableName = DesktopCellDatabase.NAME,
        indices = {@Index(value = DesktopCellDatabase.ROW_ID, unique = true),
                @Index(value = DesktopCellDatabase.ROW_POSITION, unique = true)})
public class DesktopCellEntity implements DesktopCellModel {

    public static final class CellType {
        public static final int APP = 0;
        public static final int CONTACT = 1;
        public static final int URL = 2;
    }

    @ColumnInfo(name = DesktopCellDatabase.ROW_ID)
    private int mAppId;

    @ColumnInfo(name = DesktopCellDatabase.ROW_POSITION)
    private int mPosition;

    @ColumnInfo(name = DesktopCellDatabase.ROW_SCREEN)
    private int mScreen;

    @ColumnInfo(name = DesktopCellDatabase.ROW_CELL_TYPE)
    private int mCellType;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = DesktopCellDatabase.ROW_INTENT)
    private Intent mIntent;

    public DesktopCellEntity(final int appId, final int position, final int screen,
                             final int cellType, final Intent intent) {
        mAppId = appId;
        mPosition = position;
        mScreen = screen;
        mCellType = cellType;
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
    public int getCellType() {
        return mCellType;
    }

    @Override
    public Intent getIntent() {
        return mIntent;
    }

}
