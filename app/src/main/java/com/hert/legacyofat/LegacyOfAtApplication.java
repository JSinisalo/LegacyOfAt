package com.hert.legacyofat;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * The type Legacy of at application.
 */
public class LegacyOfAtApplication extends Application {

    /**
     * Gets ref watcher.
     *
     * @param context the context
     * @return the ref watcher
     */
    public static RefWatcher getRefWatcher(Context context) {

        LegacyOfAtApplication application = (LegacyOfAtApplication) context.getApplicationContext();

        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {

        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {

            return;
        }

        refWatcher = LeakCanary.install(this);
    }
}
