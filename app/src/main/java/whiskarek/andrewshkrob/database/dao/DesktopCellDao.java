package whiskarek.andrewshkrob.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import whiskarek.andrewshkrob.database.ShortcutDatabase;
import whiskarek.andrewshkrob.database.entity.ShortcutEntity;

@Dao
public interface DesktopCellDao {

    @Query("SELECT * FROM " + ShortcutDatabase.NAME)
    LiveData<List<ShortcutEntity>> loadAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(final ShortcutEntity entity);

}
