package whiskarek.andrewshkrob.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import whiskarek.andrewshkrob.database.entity.ApplicationEntity;

@Dao
public interface ApplicationDao {

    @Query("SELECT * FROM Applications")
    List<ApplicationEntity> loadAll();

    @Query("SELECT * FROM Applications")
    LiveData<List<ApplicationEntity>> loadAllApplications();

    @Query("SELECT COUNT(*) FROM Applications")
    long count();

    @Query("SELECT LaunchAmount FROM Applications WHERE PackageName LIKE :packageName")
    int getLaunchAmount(final String packageName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(final List<ApplicationEntity> apps);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertApp(final ApplicationEntity app);

    @Query("UPDATE Applications SET LaunchAmount = :launchAmount WHERE PackageName LIKE :packageName")
    void updateLaunches(final String packageName, final int launchAmount);

    @Query("DELETE FROM Applications WHERE PackageName LIKE :packageName")
    void delete(final String packageName);

    @Delete
    void delete(List<ApplicationEntity> app);
}
