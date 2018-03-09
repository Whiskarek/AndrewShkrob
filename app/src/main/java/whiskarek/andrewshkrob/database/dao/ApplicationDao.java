package whiskarek.andrewshkrob.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import whiskarek.andrewshkrob.database.ApplicationDatabase;
import whiskarek.andrewshkrob.database.entity.ApplicationEntity;

@Dao
public interface ApplicationDao {

    //----------------------------------------------------------------------------------------------

    @Query("SELECT COUNT(*) FROM " + ApplicationDatabase.NAME)
    long count();

    //----------------------------------------------------------------------------------------------

    @Query("SELECT * FROM " + ApplicationDatabase.NAME)
    LiveData<List<ApplicationEntity>> loadAllApplications();

    @Query("SELECT * FROM " + ApplicationDatabase.NAME +
            " ORDER BY " + ApplicationDatabase.ROW_LAUNCH_AMOUNT +
            " DESC LIMIT :amount")
    LiveData<List<ApplicationEntity>> loadMostUsed(final int amount);

    @Query("SELECT " + ApplicationDatabase.ROW_PACKAGE_NAME +
            " FROM " + ApplicationDatabase.NAME)
    List<String> loadAllPackages();

    @Query("SELECT " + ApplicationDatabase.ROW_PACKAGE_NAME +
            " FROM " + ApplicationDatabase.NAME +
            " WHERE " + ApplicationDatabase.ROW_PACKAGE_NAME + " LIKE :packageName LIMIT 1")
    String getIconPath(final String packageName);

    @Query("SELECT * FROM " + ApplicationDatabase.NAME +
            " WHERE " + ApplicationDatabase.ROW_ID + " LIKE :id")
    ApplicationEntity getAppWithId(final int id);

    @Query("SELECT * FROM " + ApplicationDatabase.NAME +
            " WHERE " + ApplicationDatabase.ROW_PACKAGE_NAME + " LIKE :packageName")
    ApplicationEntity getAppWithPackage(final String packageName);

    //----------------------------------------------------------------------------------------------

    @Query("UPDATE " + ApplicationDatabase.NAME +
            " SET " + ApplicationDatabase.ROW_LAUNCH_AMOUNT + " = :launchAmount" +
            " WHERE " + ApplicationDatabase.ROW_PACKAGE_NAME + " LIKE :packageName")
    void setLaunchAmount(final String packageName, final int launchAmount);

    //------------------------------------INSERT----------------------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(final ApplicationEntity app);

    //------------------------------------DELETE----------------------------------------------------

    @Query("DELETE FROM " + ApplicationDatabase.NAME +
            " WHERE " + ApplicationDatabase.ROW_PACKAGE_NAME + " LIKE :packageName")
    void delete(final String packageName);

}
