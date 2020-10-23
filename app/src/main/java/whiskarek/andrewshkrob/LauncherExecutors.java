package whiskarek.andrewshkrob;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LauncherExecutors {
    private static LauncherExecutors sInstance;

    private final Executor mDatabaseIO;
    private final Executor mNetworkIO;
    private final Executor mMainThreadIO;

    private LauncherExecutors(final Executor databaseIO, final Executor networkIO,
                              final Executor mainThreadIO) {
        this.mDatabaseIO = databaseIO;
        this.mNetworkIO = networkIO;
        this.mMainThreadIO = mainThreadIO;
    }

    private LauncherExecutors() {
        this(Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(3),
                new MainThreadExecutor());
    }

    public static LauncherExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LauncherExecutors.class) {
                if (sInstance == null) {
                    sInstance = new LauncherExecutors();
                }
            }
        }

        return sInstance;
    }

    public Executor databaseIO() {
        return mDatabaseIO;
    }

    public Executor networkIO() {
        return mNetworkIO;
    }

    public Executor mainThreadIO() {
        return mMainThreadIO;
    }

    private static class MainThreadExecutor implements Executor {
        private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull final Runnable command) {
            mMainThreadHandler.post(command);
        }
    }
}
