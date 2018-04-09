package com.hert.legacyofat;

import android.app.Application;
import android.content.Context;

import com.hert.legacyofat.misc.Debug;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by juhos on 9.4.2018.
 */

public class LegacyOfAtApplication extends Application {

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

        Debug.log("yo");
    }
}
