package com.example.a18.path;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;

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
    }
}
