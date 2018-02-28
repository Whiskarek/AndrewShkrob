package whiskarek.andrewshkrob;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LauncherExecutors {
    private static LauncherExecutors sInstance;

    private final Executor mDiskIO;
    private final Executor mNetworkIO;
    private final Executor mMainThreadIO;

    private LauncherExecutors(final Executor diskIO, final Executor networkIO,
                              final Executor mainThreadIO) {
        this.mDiskIO = diskIO;
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

    public Executor diskIO() {
        return mDiskIO;
    }

    public Executor networkIO() {
        return mNetworkIO;
    }

    public Executor mainThreadIO() {
        return mMainThreadIO;
    }

    private static class MainThreadExecutor implements Executor {
        private Handler mMainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull final Runnable command) {
            mMainThreadHandler.post(command);
        }
    }
}
