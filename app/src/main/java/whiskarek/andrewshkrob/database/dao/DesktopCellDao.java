package whiskarek.andrewshkrob.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import whiskarek.andrewshkrob.database.DesktopCellDatabase;
import whiskarek.andrewshkrob.database.entity.DesktopCellEntity;

@Dao
public interface DesktopCellDao {

    @Query("SELECT * FROM " + DesktopCellDatabase.NAME)
    LiveData<List<DesktopCellEntity>> loadAll();

    @Insert
    void insert(final DesktopCellEntity entity);

}
