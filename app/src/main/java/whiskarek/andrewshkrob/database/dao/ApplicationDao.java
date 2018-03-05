package whiskarek.andrewshkrob.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import whiskarek.andrewshkrob.database.ApplicationsDatabase;
import whiskarek.andrewshkrob.database.entity.ApplicationEntity;

@Dao
public interface ApplicationDao {

    //----------------------------------------------------------------------------------------------

    @Query("SELECT COUNT(*) FROM " + ApplicationsDatabase.NAME)
    long count();

    //----------------------------------------------------------------------------------------------

    @Query("SELECT * FROM " + ApplicationsDatabase.NAME)
    LiveData<List<ApplicationEntity>> loadAllApplications();

    @Query("SELECT " + ApplicationsDatabase.ROW_PACKAGE_NAME +
            " FROM " + ApplicationsDatabase.NAME)
    List<String> loadAllPackages();

    @Query("SELECT " + ApplicationsDatabase.ROW_PACKAGE_NAME +
            " FROM " + ApplicationsDatabase.NAME +
            " WHERE " + ApplicationsDatabase.ROW_PACKAGE_NAME + " LIKE :packageName LIMIT 1")
    String getIconPath(final String packageName);

    @Query("SELECT * FROM " + ApplicationsDatabase.NAME +
            " WHERE " + ApplicationsDatabase.ROW_ID + " LIKE :id")
    ApplicationEntity getAppWithId(final int id);

    @Query("SELECT * FROM " + ApplicationsDatabase.NAME +
            " WHERE " + ApplicationsDatabase.ROW_PACKAGE_NAME + " LIKE :packageName")
    ApplicationEntity getAppWithPackage(final String packageName);

    //----------------------------------------------------------------------------------------------

    @Query("UPDATE " + ApplicationsDatabase.NAME +
            " SET " + ApplicationsDatabase.ROW_PACKAGE_NAME + " = :launchAmount" +
            " WHERE " + ApplicationsDatabase.ROW_PACKAGE_NAME + " LIKE :packageName")
    void setLaunchAmount(final String packageName, final int launchAmount);

    //------------------------------------INSERT----------------------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(final ApplicationEntity app);

    //------------------------------------DELETE----------------------------------------------------

    @Query("DELETE FROM " + ApplicationsDatabase.NAME +
            " WHERE " + ApplicationsDatabase.ROW_PACKAGE_NAME + " LIKE :packageName")
    void delete(final String packageName);

}
