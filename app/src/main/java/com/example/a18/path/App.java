package com.example.a18.path;

import android.app.Application;
import android.content.Context;

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
    }
}
