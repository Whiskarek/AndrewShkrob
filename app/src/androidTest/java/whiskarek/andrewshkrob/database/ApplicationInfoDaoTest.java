package whiskarek.andrewshkrob.database;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import whiskarek.andrewshkrob.database.dao.ApplicationInfoDao;
import whiskarek.andrewshkrob.database.entity.ApplicationInfoEntity;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static whiskarek.andrewshkrob.database.TestData.APPLICATIONS;
import static whiskarek.andrewshkrob.database.TestData.APPLICATIONS_WITH_SAME_INTENT;
import static whiskarek.andrewshkrob.database.TestData.APPLICATION_INFO_ENTITY1;

@RunWith(AndroidJUnit4.class)
public class ApplicationInfoDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private LauncherDatabase mDatabase;

    private ApplicationInfoDao mApplicationInfoDao;

    @Before
    public void initDb() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                LauncherDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        mApplicationInfoDao = mDatabase.applicationInfoDao();
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void getAppsWhenNoAppInserted() throws InterruptedException {
        final List<ApplicationInfoEntity> products = mApplicationInfoDao.loadAll();

        assertTrue(products.isEmpty());
    }

    @Test
    public void getAppsAfterInserted() throws InterruptedException {
        mApplicationInfoDao.insertAll(APPLICATIONS);

        final List<ApplicationInfoEntity> products = mApplicationInfoDao.loadAll();

        assertThat(products.size(), is(APPLICATIONS.size()));
    }

    @Test
    public void getAppsAfterAppsWithSameIntentInserted() throws InterruptedException {
        mApplicationInfoDao.insertAll(APPLICATIONS_WITH_SAME_INTENT);

        final List<ApplicationInfoEntity> products = mApplicationInfoDao.loadAll();

        assertThat(products.size(), is(APPLICATIONS.size() - 1));
        assertThat(products.size(), not(APPLICATIONS.size()));
    }

    @Test
    public void getAppInfo() throws InterruptedException {
        mApplicationInfoDao.insertAll(APPLICATIONS);

        final ApplicationInfoEntity app =
                mApplicationInfoDao.getApp(APPLICATION_INFO_ENTITY1.getPackageName());

        assertThat(app.getPackageName(), is(APPLICATION_INFO_ENTITY1.getPackageName()));
        assertThat(app.getInstallTime(), is(APPLICATION_INFO_ENTITY1.getInstallTime()));
        assertThat(app.getLaunchAmount(), is(APPLICATION_INFO_ENTITY1.getLaunchAmount()));
        assertThat(app.getIntent(), is(APPLICATION_INFO_ENTITY1.getIntent()));
    }

}
