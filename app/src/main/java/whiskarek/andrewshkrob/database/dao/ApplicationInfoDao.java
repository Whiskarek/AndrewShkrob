package whiskarek.andrewshkrob.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import whiskarek.andrewshkrob.database.LauncherDatabase;
import whiskarek.andrewshkrob.database.entity.ApplicationInfoEntity;

@Dao
public interface ApplicationInfoDao {

    //----------------------------------------------------------------------------------------------

    @Query("SELECT COUNT(*) FROM " + LauncherDatabase.DATABASE_APPS_NAME)
    long count();

    //----------------------------------------------------------------------------------------------

    @Query("SELECT * FROM " + LauncherDatabase.DATABASE_APPS_NAME)
    List<ApplicationInfoEntity> loadAll();

    @Query("SELECT * FROM " + LauncherDatabase.DATABASE_APPS_NAME)
    LiveData<List<ApplicationInfoEntity>> loadAllApplications();

    @Query("SELECT " + LauncherDatabase.DATABASE_ROW_PACKAGE_NAME +
            " FROM " + LauncherDatabase.DATABASE_APPS_NAME)
    List<String> loadAllPackages();

    @Query("SELECT * FROM " + LauncherDatabase.DATABASE_APPS_NAME +
            " WHERE " + LauncherDatabase.DATABASE_ROW_PACKAGE_NAME +
            " LIKE :packageName")
    ApplicationInfoEntity getApp(final String packageName);

    @Query("SELECT " + LauncherDatabase.DATABASE_ROW_ICON_PATH +
            " FROM " + LauncherDatabase.DATABASE_APPS_NAME +
            " WHERE " + LauncherDatabase.DATABASE_ROW_PACKAGE_NAME + " LIKE :packageName LIMIT 1")
    String getIconPath(final String packageName);

    //----------------------------------------------------------------------------------------------

    @Query("UPDATE " + LauncherDatabase.DATABASE_APPS_NAME +
            " SET " + LauncherDatabase.DATABASE_ROW_LAUNCH_AMOUNT + " = :launchAmount" +
            " WHERE " + LauncherDatabase.DATABASE_ROW_PACKAGE_NAME + " LIKE :packageName")
    void setLaunchAmount(final String packageName, final int launchAmount);

    //------------------------------------INSERT----------------------------------------------------

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(final List<ApplicationInfoEntity> apps);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(final ApplicationInfoEntity app);

    //------------------------------------DELETE----------------------------------------------------

    @Query("DELETE FROM " + LauncherDatabase.DATABASE_APPS_NAME +
            " WHERE " + LauncherDatabase.DATABASE_ROW_PACKAGE_NAME + " LIKE :packageName")
    void delete(final String packageName);

    @Query("DELETE FROM " + LauncherDatabase.DATABASE_APPS_NAME +
            " WHERE " + LauncherDatabase.DATABASE_ROW_PACKAGE_NAME + " IN(:packageName)")
    void delete(final List<String> packageName);

    @Delete
    void delete(final ApplicationInfoEntity app);

    @Query("DELETE FROM " + LauncherDatabase.DATABASE_APPS_NAME)
    void deleteAll();

}
