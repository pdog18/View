package com.example.a18.path;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;

import org.litepal.LitePal;

import timber.log.Timber;

public class App extends MultiDexApplication {
   static Context app;

    public static Context getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app =this;
        Stetho.initializeWithDefaults(this);
        LitePal.initialize(this);


        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(@NonNull StackTraceElement element) {
                /* Create Tag like this :
                   (AppWrap.java:83)      message ....
                   help the log in the  logcat terminal can click!
                 */
                String tag = " (" + element.getFileName() + ":" + element.getLineNumber() + ") ";
                return tag + super.createStackElementTag(element);
            }
        });


        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Timber.d("%s  onActivityCreated", activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Timber.d("%s  onActivityStarted", activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Timber.d("%s  onActivityResumed", activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Timber.d("%s  onActivityPaused", activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Timber.d("%s  onActivityStopped", activity.getClass().getSimpleName());
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Timber.d("%s  onActivitySaveInstanceState", activity.getClass().getSimpleName());
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Timber.d("%s  onActivityDestroyed", activity.getClass().getSimpleName());
            }
        });
    }
}
