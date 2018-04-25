package com.example.a18.path;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class Utils {
    private static final DisplayMetrics metrics = App.app.getResources().getDisplayMetrics();

//    private static final float density = App.getApp().getResources().getDisplayMetrics().density;
//    public static float dp(float px) {
//        return (px / density + 0.5f);
//    }

    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, metrics);

    }

    public static Context getApp() {
        return App.app;
    }
}
