package com.example.a18.path;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import timber.log.Timber;

/**
 * Created by pdog on 2017/12/9.
 */

public class App extends Application {
   static Context app;

    public static Context getApp() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app =this;

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
